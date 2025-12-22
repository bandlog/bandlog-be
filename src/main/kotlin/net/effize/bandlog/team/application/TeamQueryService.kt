package net.effize.bandlog.team.application

import net.effize.bandlog.common.id.UserId
import net.effize.bandlog.team.adapter.`in`.web.response.TeamInfoResponse
import net.effize.bandlog.team.adapter.`in`.web.response.TeamsResponse
import net.effize.bandlog.team.api.TeamQueryPort
import net.effize.bandlog.team.application.port.UserInfoPort
import net.effize.bandlog.team.domain.MemberId
import net.effize.bandlog.team.domain.MemberRole
import net.effize.bandlog.team.domain.TeamId
import org.springframework.stereotype.Service

@Service
class TeamQueryService(
    private val teamService: TeamService,
    private val userInfoPort: UserInfoPort
) : TeamQueryPort {

    fun teamInfo(authUserId: UserId, teamId: Long): TeamInfoResponse {
        val foundTeam = teamService.activeTeam(TeamId.of(teamId))
        val members = teamService.membersOf(foundTeam)
        val meCount = members.count { it.userId() == authUserId }
        if (meCount <= 0) throw IllegalStateException("User is not a member of the team")

        val users = userInfoPort.findAllByIds(members.map { it.userId() })
        val userMap = users.associateBy { it.id.value }

        return TeamInfoResponse(
            name = foundTeam.name,
            description = foundTeam.description,
            inviteCode = foundTeam.inviteCode,
            members = members.map { member ->
                TeamInfoResponse.MemberInfo(
                    nickname = userMap[member.userId().value]?.nickname ?: "",
                    role = member.role
                )
            }
        )
    }

    fun myTeams(authUserId: UserId): TeamsResponse {
        val teams = teamService.teamsOfUser(authUserId)

        return TeamsResponse(
            teams = teams.map { team ->
                val members = teamService.membersOf(team)
                val users = userInfoPort.findAllByIds(members.map { it.userId() })
                val userMap = users.associateBy { it.id.value }

                TeamInfoResponse(
                    name = team.name,
                    description = team.description,
                    inviteCode = team.inviteCode,
                    members = members.map { member ->
                        TeamInfoResponse.MemberInfo(
                            nickname = userMap[member.userId().value]?.nickname ?: "",
                            role = member.role
                        )
                    }
                )
            }
        )
    }

    override fun getTeamIdsOfUser(userId: UserId): List<Long> {
        val teams = teamService.teamsOfUser(userId)
        return teams.map { it.id().value }
    }

    override fun isUserLeaderOfTeam(userId: UserId, teamId: Long): Boolean {
        val team = teamService.activeTeam(TeamId.of(teamId))
        return teamService.membersOf(team)
            .filter { it.userId() == userId }
            .any { it.role == MemberRole.LEADER }
    }

    override fun isMemberOfTeam(userId: UserId, teamId: Long): Boolean {
        val team = teamService.activeTeam(TeamId.of(teamId))
        return teamService.membersOf(team).any { it.userId() == userId }
    }

    override fun getNicknameOfMember(memberId: Long): String {
        val member = teamService.memberOf(MemberId(memberId))
        return userInfoPort.findById(member.userId())?.nickname ?: ""
    }
}
