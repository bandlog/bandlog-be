package net.effize.bandlog.team.application

import net.effize.bandlog.common.id.UserId
import net.effize.bandlog.team.adapter.`in`.web.request.CreateTeamRequest
import net.effize.bandlog.team.adapter.`in`.web.request.JoinTeamRequest
import net.effize.bandlog.team.adapter.`in`.web.request.RefreshTeamInviteCodeRequest
import net.effize.bandlog.team.adapter.`in`.web.response.CreateTeamResponse
import net.effize.bandlog.team.adapter.`in`.web.response.JoinTeamResponse
import net.effize.bandlog.team.adapter.`in`.web.response.RefreshTeamInviteCodeResponse
import net.effize.bandlog.team.domain.MemberRole
import net.effize.bandlog.team.domain.TeamId
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class TeamCommandService(
    private val teamService: TeamService
) {

    fun createTeam(authUserId: UserId, request: CreateTeamRequest): CreateTeamResponse {
        val now = Instant.now()
        val createdTeam = teamService.createTeam(request.name, request.description, now)
        teamService.addNewMember(createdTeam, authUserId, MemberRole.LEADER, now)

        return CreateTeamResponse(createdTeam.id().value)
    }

    fun refreshTeamInviteCode(authUserId: UserId, request: RefreshTeamInviteCodeRequest): RefreshTeamInviteCodeResponse {
        val foundTeam = teamService.activeTeam(TeamId.of(request.teamId))
        val members = teamService.membersOf(foundTeam)
        val meLeaderCount = members
            .filter { it.userId() == authUserId }
            .count { it.role == MemberRole.LEADER }

        if (meLeaderCount <= 0) throw IllegalStateException("User is not a leader of the team")

        foundTeam.refreshInviteCode()
        return RefreshTeamInviteCodeResponse(foundTeam.id().value)
    }

    fun joinTeam(authUserId: UserId, request: JoinTeamRequest): JoinTeamResponse {
        val now = Instant.now()
        val foundTeam = teamService.activeTeam(TeamId.of(request.teamId))
        val members = teamService.membersOf(foundTeam)
        val meCount = members.count { it.userId() == authUserId }

        if (meCount > 0) throw IllegalStateException("User already joined the team")

        teamService.addNewMember(foundTeam, authUserId, MemberRole.MEMBER, now)

        return JoinTeamResponse(foundTeam.id().value)
    }
}
