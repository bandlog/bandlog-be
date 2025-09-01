package net.effize.bandlog.team.application.dto;

public record JoinTeamRequest(
        Long teamId,
        String inviteCode
) {
}
