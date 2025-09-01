package net.effize.bandlog.application.team.dto;

import java.util.List;

public record TeamsResponse(
        List<TeamInfoResponse> teams
) {
}
