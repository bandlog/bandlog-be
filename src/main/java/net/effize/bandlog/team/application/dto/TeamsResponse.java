package net.effize.bandlog.team.application.dto;

import java.util.List;

public record TeamsResponse(
        List<TeamInfoResponse> teams
) {
}
