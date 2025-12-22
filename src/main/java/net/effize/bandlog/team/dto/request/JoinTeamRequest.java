package net.effize.bandlog.team.dto.request;

public record JoinTeamRequest(
        Long teamId,
        String inviteCode
) {
}
