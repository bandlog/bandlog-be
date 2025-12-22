package net.effize.bandlog.rehearsal.adapter.out.team

import net.effize.bandlog.common.id.UserId
import net.effize.bandlog.rehearsal.application.port.TeamInfoPort
import net.effize.bandlog.team.api.TeamQueryPort
import org.springframework.stereotype.Component

@Component
class TeamInfoAdapter(
    private val teamQueryPort: TeamQueryPort
) : TeamInfoPort {
    override fun getTeamIdsOfUser(userId: UserId): List<Long> {
        return teamQueryPort.getTeamIdsOfUser(userId)
    }

    override fun isUserLeaderOfTeam(userId: UserId, teamId: Long): Boolean {
        return teamQueryPort.isUserLeaderOfTeam(userId, teamId)
    }

    override fun isMemberOfTeam(memberId: Long, teamId: Long): Boolean {
        return teamQueryPort.isMemberOfTeam(UserId(memberId), teamId)
    }

    override fun getNicknameOfMember(memberId: Long): String {
        return teamQueryPort.getNicknameOfMember(memberId)
    }
}
