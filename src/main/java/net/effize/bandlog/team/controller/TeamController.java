package net.effize.bandlog.team.controller;

import net.effize.bandlog.auth.api.AuthenticationPrincipal;
import net.effize.bandlog.identity.api.UserService;
import net.effize.bandlog.identity.api.dto.UserDto;
import net.effize.bandlog.team.application.TeamCommandService;
import net.effize.bandlog.team.application.TeamQueryService;
import net.effize.bandlog.team.application.dto.*;
import net.effize.bandlog.team.domain.model.UserId;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamCommandService teamCommandService;
    private final TeamQueryService teamQueryService;
    private final UserService userService;

    public TeamController(TeamCommandService teamCommandService, TeamQueryService teamQueryService, UserService userService) {
        this.teamCommandService = teamCommandService;
        this.teamQueryService = teamQueryService;
        this.userService = userService;
    }

    private Long getUserIdFromAuthentication(Authentication authentication) {
        AuthenticationPrincipal principal = (AuthenticationPrincipal) authentication.getPrincipal();
        UserDto user = userService.findBySupabaseUserId(principal.supabaseUserId());
        return user.id();
    }

    @PostMapping
    public ResponseEntity<CreateTeamResponse> createTeam(
            Authentication authentication,
            @RequestBody CreateTeamRequest request) {
        Long userId = getUserIdFromAuthentication(authentication);
        UserId teamUserId = UserId.of(userId);
        CreateTeamResponse response = teamCommandService.createTeam(teamUserId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my")
    public ResponseEntity<TeamsResponse> getMyTeams(Authentication authentication) {
        Long userId = getUserIdFromAuthentication(authentication);
        UserId teamUserId = UserId.of(userId);
        TeamsResponse response = teamQueryService.myTeams(teamUserId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<TeamInfoResponse> getTeamInfo(
            Authentication authentication,
            @PathVariable Long teamId) {
        Long userId = getUserIdFromAuthentication(authentication);
        UserId teamUserId = UserId.of(userId);
        TeamInfoResponse response = teamQueryService.teamInfo(teamUserId, teamId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{teamId}/join")
    public ResponseEntity<JoinTeamResponse> joinTeam(
            Authentication authentication,
            @PathVariable Long teamId,
            @RequestBody JoinTeamRequest request) {
        Long userId = getUserIdFromAuthentication(authentication);
        UserId teamUserId = UserId.of(userId);
        JoinTeamResponse response = teamCommandService.joinTeam(teamUserId, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{teamId}/invite-code/refresh")
    public ResponseEntity<RefreshTeamInviteCodeResponse> refreshInviteCode(
            Authentication authentication,
            @PathVariable Long teamId) {
        Long userId = getUserIdFromAuthentication(authentication);
        UserId teamUserId = UserId.of(userId);
        RefreshTeamInviteCodeRequest request = new RefreshTeamInviteCodeRequest(teamId);
        RefreshTeamInviteCodeResponse response = teamCommandService.refreshTeamInviteCode(teamUserId, request);
        return ResponseEntity.ok(response);
    }
}