package net.effize.bandlog.api.team;

import net.effize.bandlog.api.config.resolver.AuthUserParam;
import net.effize.bandlog.application.auth.dto.AuthUser;
import net.effize.bandlog.application.team.TeamCommandService;
import net.effize.bandlog.application.team.TeamQueryService;
import net.effize.bandlog.application.team.dto.*;
import org.springframework.web.bind.annotation.*;

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
    public CreateTeamResponse createTeam(@AuthUserParam AuthUser authUser, @RequestBody CreateTeamRequest request) {
        return teamCommandService.createTeam(authUser.id(), request);
    }

    @PostMapping("/refresh-invite-code")
    public RefreshTeamInviteCodeResponse refreshTeamInviteCode(@AuthUserParam AuthUser authUser, @RequestBody RefreshTeamInviteCodeRequest request) {
        return teamCommandService.refreshTeamInviteCode(authUser.id(), request);
    }

    @PostMapping("/join")
    public JoinTeamResponse joinTeam(@AuthUserParam AuthUser authUser, @RequestBody JoinTeamRequest request) {
        return teamCommandService.joinTeam(authUser.id(), request);
    }

    @GetMapping("/{teamId}")
    public TeamInfoResponse getTeamInfoResponse(@AuthUserParam AuthUser authUser, @PathVariable Long teamId) {
        return teamQueryService.teamInfo(authUser.id(), teamId);
    }
}
