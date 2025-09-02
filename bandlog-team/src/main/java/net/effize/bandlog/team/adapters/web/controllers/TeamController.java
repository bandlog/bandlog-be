package net.effize.bandlog.team.adapters.web.controllers;

import net.effize.bandlog.team.application.TeamApplicationService;
import net.effize.bandlog.team.adapters.web.dto.*;
import net.effize.bandlog.shared.kernel.types.UserId;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/teams")
public class TeamController {
    private final TeamApplicationService teamApplicationService;

    public TeamController(TeamApplicationService teamApplicationService) {
        this.teamApplicationService = teamApplicationService;
    }

    @PostMapping
    public CreateTeamResponse createTeam(@RequestParam("userId") Long userId, @RequestBody CreateTeamRequest request) {
        return teamApplicationService.createTeam(request.name(), request.description(), UserId.of(userId));
    }

    @PostMapping("/refresh-invite-code")
    public RefreshTeamInviteCodeResponse refreshTeamInviteCode(@RequestParam("userId") Long userId, @RequestBody RefreshTeamInviteCodeRequest request) {
        return teamApplicationService.refreshTeamInviteCode(UserId.of(userId), request.teamId());
    }

    @PostMapping("/join")
    public JoinTeamResponse joinTeam(@RequestParam("userId") Long userId, @RequestBody JoinTeamRequest request) {
        return teamApplicationService.joinTeam(UserId.of(userId), request.inviteCode());
    }

    @GetMapping("/{teamId}")
    public TeamInfoResponse getTeamInfo(@RequestParam("userId") Long userId, @PathVariable Long teamId) {
        return teamApplicationService.getTeamInfo(UserId.of(userId), teamId);
    }

    @GetMapping("/me")
    public TeamsResponse getMyTeams(@RequestParam("userId") Long userId) {
        return teamApplicationService.getMyTeams(UserId.of(userId));
    }
}