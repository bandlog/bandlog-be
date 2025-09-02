package net.effize.bandlog.team.adapters.web.dto;

public record JoinTeamResponse(
    Long teamId,
    String teamName,
    String role
) {
}