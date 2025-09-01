package net.effize.bandlog.application.team.dto;

public record JoinTeamRequest(
        Long teamId,
        String inviteCode
) {
}
