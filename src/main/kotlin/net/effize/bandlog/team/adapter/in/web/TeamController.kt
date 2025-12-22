package net.effize.bandlog.team.adapter.`in`.web

import net.effize.bandlog.common.auth.AuthUser
import net.effize.bandlog.common.id.UserId
import net.effize.bandlog.team.adapter.`in`.web.request.CreateTeamRequest
import net.effize.bandlog.team.adapter.`in`.web.request.JoinTeamRequest
import net.effize.bandlog.team.adapter.`in`.web.request.RefreshTeamInviteCodeRequest
import net.effize.bandlog.team.adapter.`in`.web.response.CreateTeamResponse
import net.effize.bandlog.team.adapter.`in`.web.response.JoinTeamResponse
import net.effize.bandlog.team.adapter.`in`.web.response.RefreshTeamInviteCodeResponse
import net.effize.bandlog.team.adapter.`in`.web.response.TeamInfoResponse
import net.effize.bandlog.team.adapter.`in`.web.response.TeamsResponse
import net.effize.bandlog.team.application.TeamCommandService
import net.effize.bandlog.team.application.TeamQueryService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/teams")
class TeamController(
    private val teamQueryService: TeamQueryService,
    private val teamCommandService: TeamCommandService
) {

    @PostMapping
    fun createTeam(authUser: AuthUser, @RequestBody request: CreateTeamRequest): CreateTeamResponse {
        return teamCommandService.createTeam(UserId(authUser.id), request)
    }

    @PostMapping("/refresh-invite-code")
    fun refreshTeamInviteCode(authUser: AuthUser, @RequestBody request: RefreshTeamInviteCodeRequest): RefreshTeamInviteCodeResponse {
        return teamCommandService.refreshTeamInviteCode(UserId(authUser.id), request)
    }

    @PostMapping("/join")
    fun joinTeam(authUser: AuthUser, @RequestBody request: JoinTeamRequest): JoinTeamResponse {
        return teamCommandService.joinTeam(UserId(authUser.id), request)
    }

    @GetMapping("/{teamId}")
    fun getTeamInfoResponse(authUser: AuthUser, @PathVariable teamId: Long): TeamInfoResponse {
        return teamQueryService.teamInfo(UserId(authUser.id), teamId)
    }

    @GetMapping("/me")
    fun getMyTeams(authUser: AuthUser): TeamsResponse {
        return teamQueryService.myTeams(UserId(authUser.id))
    }
}
