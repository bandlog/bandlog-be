package net.effize.bandlog.rehearsal.service

import net.effize.bandlog.rehearsal.adapter.out.team.TeamAdapter
import net.effize.bandlog.rehearsal.dto.request.AddRehearsalSongInstrumentRequest
import net.effize.bandlog.rehearsal.dto.request.AddRehearsalSongRequest
import net.effize.bandlog.rehearsal.dto.request.AssignMemberToInstrumentRequest
import net.effize.bandlog.rehearsal.dto.request.CreateRehearsalRequest
import net.effize.bandlog.rehearsal.dto.request.ModifyRehearsalSongRequest
import net.effize.bandlog.rehearsal.dto.response.AddRehearsalSongInstrumentResponse
import net.effize.bandlog.rehearsal.dto.response.AddRehearsalSongResponse
import net.effize.bandlog.rehearsal.dto.response.AssignMemberToInstrumentResponse
import net.effize.bandlog.rehearsal.dto.response.CreateRehearsalResponse
import net.effize.bandlog.rehearsal.dto.response.ModifyRehearsalSongResponse
import net.effize.bandlog.rehearsal.model.Rehearsal
import net.effize.bandlog.rehearsal.repository.RehearsalRepository
import net.effize.bandlog.shared.auth.AuthUser
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RehearsalCommandUseCase(
    private val rehearsalRepository: RehearsalRepository,
    private val teamAdapter: TeamAdapter
) {
    @Transactional
    fun createRehearsal(authUser: AuthUser, createRehearsalRequest: CreateRehearsalRequest): CreateRehearsalResponse {
        if (!teamAdapter.isUserLeaderOfTeam(authUser.id, createRehearsalRequest.teamId)) {
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

    @Transactional
    fun addRehearsalSong(
        authUser: AuthUser,
        rehearsalId: Long,
        addRehearsalSongRequest: AddRehearsalSongRequest
    ): AddRehearsalSongResponse {
        if (!teamAdapter.isUserLeaderOfTeam(authUser.id, addRehearsalSongRequest.teamId)) {
            throw IllegalStateException("Cannot add song to rehearsal you are not leader of")
        }

        val rehearsal = rehearsalRepository.findByIdOrNull(rehearsalId)
            ?: throw IllegalArgumentException("Rehearsal with id $rehearsalId not found")

        rehearsal.addSongWithTitle(addRehearsalSongRequest.title)

        return AddRehearsalSongResponse(rehearsal.id)
    }

    @Transactional
    fun modifyRehearsalSong(
        authUser: AuthUser,
        rehearsalId: Long,
        rehearsalSongId: Long,
        modifyRehearsalSongRequest: ModifyRehearsalSongRequest
    ): ModifyRehearsalSongResponse {
        if (!teamAdapter.isUserLeaderOfTeam(authUser.id, modifyRehearsalSongRequest.teamId)) {
            throw IllegalStateException("Cannot add song to rehearsal you are not leader of")
        }

        val rehearsal = rehearsalRepository.findByIdOrNull(rehearsalId)
            ?: throw IllegalArgumentException("Rehearsal with id $rehearsalId not found")

        rehearsal.modifySong(rehearsalSongId, modifyRehearsalSongRequest.title, modifyRehearsalSongRequest.order)

        return ModifyRehearsalSongResponse(rehearsal.id)
    }

    @Transactional
    fun addRehearsalSongInstrument(
        authUser: AuthUser,
        rehearsalId: Long,
        rehearsalSongId: Long,
        addRehearsalSongInstrumentRequest: AddRehearsalSongInstrumentRequest
    ): AddRehearsalSongInstrumentResponse {
        if (!teamAdapter.isUserLeaderOfTeam(authUser.id, addRehearsalSongInstrumentRequest.teamId)) {
            throw IllegalStateException("Cannot add instrument to rehearsal you are not leader of")
        }

        val rehearsal = rehearsalRepository.findByIdOrNull(rehearsalId)
            ?: throw IllegalArgumentException("Rehearsal with id $rehearsalId not found")

        rehearsal.addInstrumentToSong(rehearsalSongId, addRehearsalSongInstrumentRequest.instrument)

        return AddRehearsalSongInstrumentResponse(rehearsal.id)
    }

    @Transactional
    fun assignMember(
        authUser: AuthUser,
        rehearsalId: Long,
        rehearsalSongId: Long,
        rehearsalSongInstrumentId: Long,
        assignMemberRequest: AssignMemberToInstrumentRequest
    ): AssignMemberToInstrumentResponse {
        if (!teamAdapter.isUserLeaderOfTeam(authUser.id, assignMemberRequest.teamId)) {
            throw IllegalStateException("Cannot assign member to rehearsal you are not leader of")
        }
        if (!teamAdapter.isMemberOfTeam(assignMemberRequest.memberId, assignMemberRequest.teamId)) {
            throw IllegalStateException("Cannot assign member to rehearsal you are not a member of")
        }

        val rehearsal = rehearsalRepository.findByIdOrNull(rehearsalId)
            ?: throw IllegalArgumentException("Rehearsal with id $rehearsalId not found")

        rehearsal.assignMemberToInstrument(rehearsalSongId, rehearsalSongInstrumentId, assignMemberRequest.memberId)

        return AssignMemberToInstrumentResponse(rehearsal.id)
    }
}