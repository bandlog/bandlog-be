package net.effize.bandlog.rehearsal.service

import net.effize.bandlog.rehearsal.adapter.out.team.TeamAdapter
import net.effize.bandlog.rehearsal.dto.request.AddRehearsalSongRequest
import net.effize.bandlog.rehearsal.dto.request.ModifyRehearsalSongRequest
import net.effize.bandlog.rehearsal.dto.response.AddRehearsalSongResponse
import net.effize.bandlog.rehearsal.dto.response.ModifyRehearsalSongResponse
import net.effize.bandlog.rehearsal.repository.RehearsalRepository
import net.effize.bandlog.shared.auth.AuthUser
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RehearsalSongCommandUseCase(
    private val rehearsalRepository: RehearsalRepository,
    private val teamAdapter: TeamAdapter
) {

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
}