package net.effize.bandlog.rehearsal.adapter.out.team

import net.effize.bandlog.port.team.BandlogTeamPort
import org.springframework.stereotype.Component

@Component
class TeamAdapter(
    private val teamPort: BandlogTeamPort
) {
    fun teamIdsOfUser(userId: Long): List<Long> {
        return teamPort.teamIdsOfUser(userId)
    }

    fun nicknameOfMember(memberId: Long): String {
        return teamPort.nicknameOfMember(memberId)
    }

    fun isUserLeaderOfTeam(userId: Long, teamId: Long): Boolean {
        return teamPort.isUserLeaderOfTeam(userId, teamId)
    }

    fun isMemberOfTeam(memberId: Long, teamId: Long): Boolean {
        return teamPort.isMemberOfTeam(memberId, teamId)
    }
}