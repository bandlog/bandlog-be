package net.effize.bandlog.team.adapters.web.dto;

public record CreateTeamResponse(
    Long teamId,
    String name,
    String description,
    String inviteCode
) {
}