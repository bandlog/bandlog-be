package net.effize.bandlog.rehearsal.adapter.out.team

import net.effize.bandlog.port.team.BandlogTeamPort
import org.springframework.stereotype.Component

@Component
class TeamAdapter(
    private val teamPort: BandlogTeamPort
) {
    fun isUserLeaderOfTeam(userId: Long, teamId: Long): Boolean {
        return teamPort.isUserLeaderOfTeam(userId, teamId)
    }

    fun isMemberOfTeam(memberId: Long, teamId: Long): Boolean {
        return teamPort.isMemberOfTeam(memberId, teamId)
    }
}