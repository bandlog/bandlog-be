package net.effize.bandlog.rehearsal.application

import net.effize.bandlog.rehearsal.adapter.`in`.web.request.AddRehearsalSongInstrumentRequest
import net.effize.bandlog.rehearsal.adapter.`in`.web.request.AddRehearsalSongRequest
import net.effize.bandlog.rehearsal.adapter.`in`.web.request.AssignMemberToInstrumentRequest
import net.effize.bandlog.rehearsal.adapter.`in`.web.request.CreateRehearsalRequest
import net.effize.bandlog.rehearsal.adapter.`in`.web.request.ModifyRehearsalRequest
import net.effize.bandlog.rehearsal.adapter.`in`.web.request.ModifyRehearsalSongInstrumentRequest
import net.effize.bandlog.rehearsal.adapter.`in`.web.request.ModifyRehearsalSongRequest
import net.effize.bandlog.rehearsal.adapter.`in`.web.response.AddRehearsalSongInstrumentResponse
import net.effize.bandlog.rehearsal.adapter.`in`.web.response.AddRehearsalSongResponse
import net.effize.bandlog.rehearsal.adapter.`in`.web.response.AssignMemberToInstrumentResponse
import net.effize.bandlog.rehearsal.adapter.`in`.web.response.CreateRehearsalResponse
import net.effize.bandlog.rehearsal.adapter.`in`.web.response.ModifyRehearsalResponse
import net.effize.bandlog.rehearsal.adapter.`in`.web.response.ModifyRehearsalSongResponse
import net.effize.bandlog.rehearsal.adapter.out.persistence.RehearsalRepository
import net.effize.bandlog.rehearsal.adapter.out.team.TeamAdapter
import net.effize.bandlog.rehearsal.domain.entity.Rehearsal
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
    fun modifyRehearsal(
        authUser: AuthUser,
        rehearsalId: Long,
        modifyRehearsalRequest: ModifyRehearsalRequest
    ): ModifyRehearsalResponse {
        if (!teamAdapter.isUserLeaderOfTeam(authUser.id, modifyRehearsalRequest.teamId)) {
            throw IllegalStateException("Cannot create rehearsal for team you are not leader of")
        }

        val rehearsal = rehearsalRepository.findByIdOrNull(rehearsalId)
            ?: throw IllegalArgumentException("Rehearsal with id $rehearsalId not found")

        rehearsal.modifyRehearsal(
            modifyRehearsalRequest.title,
            modifyRehearsalRequest.description,
            modifyRehearsalRequest.scheduledAt,
            modifyRehearsalRequest.location
        )

        return ModifyRehearsalResponse(rehearsal.id)
    }


    @Transactional
    fun deleteRehearsal(authUser: AuthUser, rehearsalId: Long) {
        val rehearsal = rehearsalRepository.findByIdOrNull(rehearsalId)
            ?: throw IllegalArgumentException("Rehearsal with id $rehearsalId not found")

        if (!teamAdapter.isUserLeaderOfTeam(authUser.id, rehearsal.teamId)) {
            throw IllegalStateException("Cannot delete rehearsal you are not leader of")
        }

        rehearsalRepository.deleteById(rehearsalId)
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
    fun deleteRehearsalSong(authUser: AuthUser, rehearsalId: Long, rehearsalSongId: Long) {
        val rehearsal = rehearsalRepository.findByIdOrNull(rehearsalId)
            ?: throw IllegalArgumentException("Rehearsal with id $rehearsalId not found")

        if (!teamAdapter.isUserLeaderOfTeam(authUser.id, rehearsal.teamId)) {
            throw IllegalStateException("Cannot delete rehearsal song you are not leader of")
        }

        rehearsal.deleteSong(rehearsalSongId)
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
    fun modifyRehearsalSongInstrument(
        authUser: AuthUser,
        rehearsalId: Long,
        rehearsalSongId: Long,
        rehearsalSongInstrumentId: Long,
        modifyRehearsalSongInstrumentRequest: ModifyRehearsalSongInstrumentRequest
    ): ModifyRehearsalSongResponse {
        if (!teamAdapter.isUserLeaderOfTeam(authUser.id, modifyRehearsalSongInstrumentRequest.teamId)) {
            throw IllegalStateException("Cannot add instrument to rehearsal you are not leader of")
        }

        val rehearsal = rehearsalRepository.findByIdOrNull(rehearsalId)
            ?: throw IllegalArgumentException("Rehearsal with id $rehearsalId not found")

        rehearsal.modifySongInstrument(
            rehearsalSongId,
            rehearsalSongInstrumentId,
            modifyRehearsalSongInstrumentRequest.instrument,
            modifyRehearsalSongInstrumentRequest.order
        )

        return ModifyRehearsalSongResponse(rehearsal.id)
    }


    @Transactional
    fun deleteRehearsalSongInstrument(
        authUser: AuthUser,
        rehearsalId: Long,
        rehearsalSongId: Long,
        rehearsalSongInstrumentId: Long
    ) {
        val rehearsal = rehearsalRepository.findByIdOrNull(rehearsalId)
            ?: throw IllegalArgumentException("Rehearsal with id $rehearsalId not found")

        if (!teamAdapter.isUserLeaderOfTeam(authUser.id, rehearsal.teamId)) {
            throw IllegalStateException("Cannot delete rehearsal song instrument you are not leader of")
        }

        rehearsal.deleteSongInstrument(rehearsalSongId, rehearsalSongInstrumentId)
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