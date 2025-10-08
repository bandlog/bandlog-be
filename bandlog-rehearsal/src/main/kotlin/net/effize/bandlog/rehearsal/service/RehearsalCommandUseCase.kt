package net.effize.bandlog.rehearsal.service

import net.effize.bandlog.rehearsal.adapter.out.team.TeamAdapter
import net.effize.bandlog.rehearsal.dto.request.CreateRehearsalRequest
import net.effize.bandlog.rehearsal.dto.request.ModifyRehearsalRequest
import net.effize.bandlog.rehearsal.dto.response.CreateRehearsalResponse
import net.effize.bandlog.rehearsal.dto.response.ModifyRehearsalResponse
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
}