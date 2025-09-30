package net.effize.bandlog.rehearsal.service

import net.effize.bandlog.rehearsal.adapter.out.team.TeamAdapter
import net.effize.bandlog.rehearsal.dto.request.AddRehearsalSongInstrumentRequest
import net.effize.bandlog.rehearsal.dto.request.AddRehearsalSongRequest
import net.effize.bandlog.rehearsal.dto.request.AssignMemberToInstrumentRequest
import net.effize.bandlog.rehearsal.dto.request.CreateRehearsalRequest
import net.effize.bandlog.rehearsal.dto.response.AddRehearsalSongInstrumentResponse
import net.effize.bandlog.rehearsal.dto.response.AddRehearsalSongResponse
import net.effize.bandlog.rehearsal.dto.response.AssignMemberToInstrumentResponse
import net.effize.bandlog.rehearsal.dto.response.CreateRehearsalResponse
import net.effize.bandlog.rehearsal.model.Rehearsal
import net.effize.bandlog.rehearsal.model.RehearsalSong
import net.effize.bandlog.rehearsal.model.RehearsalSongInstrument
import net.effize.bandlog.rehearsal.repository.RehearsalRepository
import net.effize.bandlog.shared.auth.AuthUser
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class RehearsalCommandUseCase(
    private val rehearsalRepository: RehearsalRepository,
    private val teamAdapter: TeamAdapter
) {
    fun createRehearsal(authUser: AuthUser, createRehearsalRequest: CreateRehearsalRequest): CreateRehearsalResponse {
        if (teamAdapter.isUserLeaderOfTeam(authUser.id, createRehearsalRequest.teamId)) {
            throw IllegalStateException("Cannot create rehearsal for team you are not leader of")
        }

        val newRehearsal = Rehearsal(
            teamId = createRehearsalRequest.teamId,
            title = createRehearsalRequest.title,
            description = createRehearsalRequest.description,
            scheduledAt = createRehearsalRequest.scheduledAt,
            location = createRehearsalRequest.location
        )

        val savedRehearsal = rehearsalRepository.save(newRehearsal)

        return CreateRehearsalResponse(savedRehearsal.id)
    }

    fun addRehearsalSong(
        authUser: AuthUser,
        rehearsalId: Long,
        addRehearsalSongRequest: AddRehearsalSongRequest
    ): AddRehearsalSongResponse {
        if (teamAdapter.isUserLeaderOfTeam(authUser.id, addRehearsalSongRequest.teamId)) {
            throw IllegalStateException("Cannot add song to rehearsal you are not leader of")
        }

        val rehearsal = rehearsalRepository.findByIdOrNull(rehearsalId)
            ?: throw IllegalArgumentException("Rehearsal with id $rehearsalId not found")

        val lastSongOrder = rehearsal.lastSongOrder()
        val newSong = RehearsalSong(addRehearsalSongRequest.title, lastSongOrder + 1)

        rehearsal.addSong(newSong)

        val savedRehearsal = rehearsalRepository.save(rehearsal)

        return AddRehearsalSongResponse(savedRehearsal.id)
    }

    fun addRehearsalSongInstrument(
        authUser: AuthUser,
        rehearsalId: Long,
        rehearsalSongId: Long,
        addRehearsalSongInstrumentRequest: AddRehearsalSongInstrumentRequest
    ): AddRehearsalSongInstrumentResponse {
        if (teamAdapter.isUserLeaderOfTeam(authUser.id, addRehearsalSongInstrumentRequest.teamId)) {
            throw IllegalStateException("Cannot add instrument to rehearsal you are not leader of")
        }

        val rehearsal = rehearsalRepository.findByIdOrNull(rehearsalId)
            ?: throw IllegalArgumentException("Rehearsal with id $rehearsalId not found")

        val song = rehearsal.songs.find { it.id == rehearsalSongId }
            ?: throw IllegalArgumentException("Rehearsal song with id $rehearsalSongId not found")
        val lastInstrumentOrder = song.lastInstrumentOrder()

        val newInstrument =
            RehearsalSongInstrument(addRehearsalSongInstrumentRequest.instrument, lastInstrumentOrder + 1)
        song.addInstrument(newInstrument)

        val savedRehearsal = rehearsalRepository.save(rehearsal)

        return AddRehearsalSongInstrumentResponse(savedRehearsal.id)
    }

    fun assignMember(
        authUser: AuthUser,
        rehearsalId: Long,
        rehearsalSongId: Long,
        rehearsalSongInstrumentId: Long,
        assignMemberRequest: AssignMemberToInstrumentRequest
    ): AssignMemberToInstrumentResponse {
        if (teamAdapter.isUserLeaderOfTeam(authUser.id, assignMemberRequest.teamId)) {
            throw IllegalStateException("Cannot assign member to rehearsal you are not leader of")
        }
        if (teamAdapter.isUserMemberOfTeam(authUser.id, assignMemberRequest.teamId)) {
            throw IllegalStateException("Cannot assign member to rehearsal you are not a member of")
        }

        val rehearsal = rehearsalRepository.findByIdOrNull(rehearsalId)
            ?: throw IllegalArgumentException("Rehearsal with id $rehearsalId not found")

        val song = rehearsal.songs.find { it.id == rehearsalSongId }
            ?: throw IllegalArgumentException("Rehearsal song with id $rehearsalSongId not found")

        val instrument = rehearsalSongInstrumentId.let { song.instruments.find { i -> i.id == it } }
            ?: throw IllegalArgumentException("Rehearsal song instrument with id $rehearsalSongInstrumentId not found")

        instrument.assignMember(assignMemberRequest.memberId)

        val savedRehearsal = rehearsalRepository.save(rehearsal)

        return AssignMemberToInstrumentResponse(savedRehearsal.id)
    }
}