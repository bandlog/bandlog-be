package net.effize.bandlog.team.adapters.web.dto;

public record RefreshTeamInviteCodeResponse(
    Long teamId,
    String newInviteCode
) {
}