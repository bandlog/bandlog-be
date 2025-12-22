package net.effize.bandlog.rehearsal.application

import net.effize.bandlog.common.auth.AuthUser
import net.effize.bandlog.common.id.UserId
import net.effize.bandlog.rehearsal.adapter.`in`.web.response.RehearsalResponse
import net.effize.bandlog.rehearsal.adapter.`in`.web.response.RehearsalsResponse
import net.effize.bandlog.rehearsal.adapter.out.persistence.RehearsalRepository
import net.effize.bandlog.rehearsal.application.port.TeamInfoPort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class RehearsalQueryUseCase(
    private val rehearsalRepository: RehearsalRepository,
    private val teamInfoPort: TeamInfoPort
) {

    fun rehearsalsOf(authUser: AuthUser): RehearsalsResponse {
        val teamIds = teamInfoPort.getTeamIdsOfUser(UserId(authUser.id))

        val rehearsals = rehearsalRepository.findByTeamIdIn(teamIds)

        return RehearsalsResponse(
            rehearsals.map { rehearsal ->
                RehearsalsResponse.Rehearsal(
                    id = rehearsal.id,
                    teamId = rehearsal.teamId,
                    title = rehearsal.title,
                    scheduledAt = rehearsal.scheduledAt,
                    location = rehearsal.location
                )
            }
        )
    }

    fun rehearsalDetail(authUser: AuthUser, rehearsalId: Long): RehearsalResponse {
        val teamIds = teamInfoPort.getTeamIdsOfUser(UserId(authUser.id))
        val rehearsal = rehearsalRepository.findByIdOrNull(rehearsalId)
            ?: throw IllegalArgumentException("Rehearsal with id $rehearsalId not found")
        if (!teamIds.contains(rehearsal.teamId)) {
            throw IllegalArgumentException("Rehearsal with id $rehearsalId not found")
        }

        return RehearsalResponse(
            id = rehearsal.id,
            teamId = rehearsal.teamId,
            title = rehearsal.title,
            description = rehearsal.description,
            scheduledAt = rehearsal.scheduledAt,
            location = rehearsal.location,
            songs = rehearsal.songs.map { song ->
                RehearsalResponse.Song(
                    id = song.id,
                    title = song.title,
                    order = song.order,
                    instrumentAssignments = song.instruments.map { instrument ->
                        RehearsalResponse.Song.InstrumentAssignment(
                            instrument = instrument.instrument,
                            assignee = instrument.memberId?.let {
                                teamInfoPort.getNicknameOfMember(it)
                            },
                            order = instrument.order
                        )
                    }
                )
            }
        )
    }
}
