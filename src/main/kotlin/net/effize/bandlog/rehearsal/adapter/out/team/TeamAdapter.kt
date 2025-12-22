package net.effize.bandlog.rehearsal.adapter.out.team

import net.effize.bandlog.team.service.TeamQueryService
import org.springframework.stereotype.Component

@Component
class TeamAdapter(
    private val teamQueryService: TeamQueryService
) {
    fun teamIdsOfUser(userId: Long): List<Long> {
        return teamQueryService.teamsOfUser(userId)
    }

    fun nicknameOfMember(memberId: Long): String {
        return teamQueryService.nicknameOfMember(memberId)
    }

    fun isUserLeaderOfTeam(userId: Long, teamId: Long): Boolean {
        return teamQueryService.isUserLeaderOfTeam(userId, teamId)
    }

    fun isMemberOfTeam(memberId: Long, teamId: Long): Boolean {
        return teamQueryService.isMemberOfTeam(memberId, teamId)
    }
}
