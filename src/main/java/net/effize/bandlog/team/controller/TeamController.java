package net.effize.bandlog.team.controller;

import net.effize.bandlog.shared.auth.AuthUser;
import net.effize.bandlog.team.dto.request.CreateTeamRequest;
import net.effize.bandlog.team.dto.request.JoinTeamRequest;
import net.effize.bandlog.team.dto.request.RefreshTeamInviteCodeRequest;
import net.effize.bandlog.team.dto.response.CreateTeamResponse;
import net.effize.bandlog.team.dto.response.JoinTeamResponse;
import net.effize.bandlog.team.dto.response.RefreshTeamInviteCodeResponse;
import net.effize.bandlog.team.dto.response.TeamInfoResponse;
import net.effize.bandlog.team.dto.response.TeamsResponse;
import net.effize.bandlog.team.model.UserId;
import net.effize.bandlog.team.service.TeamCommandService;
import net.effize.bandlog.team.service.TeamQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/teams")
public class TeamController {
    private final TeamQueryService teamQueryService;
    private final TeamCommandService teamCommandService;


    public TeamController(TeamQueryService teamQueryService, TeamCommandService teamCommandService) {
        this.teamQueryService = teamQueryService;
        this.teamCommandService = teamCommandService;
    }

    @PostMapping
    public CreateTeamResponse createTeam(AuthUser authUser, @RequestBody CreateTeamRequest request) {
        return teamCommandService.createTeam(new UserId(authUser.id()), request);
    }

    @PostMapping("/refresh-invite-code")
    public RefreshTeamInviteCodeResponse refreshTeamInviteCode(AuthUser authUser, @RequestBody RefreshTeamInviteCodeRequest request) {
        return teamCommandService.refreshTeamInviteCode(new UserId(authUser.id()), request);
    }

    @PostMapping("/join")
    public JoinTeamResponse joinTeam(AuthUser authUser, @RequestBody JoinTeamRequest request) {
        return teamCommandService.joinTeam(new UserId(authUser.id()), request);
    }

    @GetMapping("/{teamId}")
    public TeamInfoResponse getTeamInfoResponse(AuthUser authUser, @PathVariable Long teamId) {
        return teamQueryService.teamInfo(new UserId(authUser.id()), teamId);
    }

    @GetMapping("/me")
    public TeamsResponse getMyTeams(AuthUser authUser) {
        return teamQueryService.myTeams(new UserId(authUser.id()));
    }
}
