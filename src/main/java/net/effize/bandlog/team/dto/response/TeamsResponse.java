package net.effize.bandlog.team.dto.response;

import java.util.List;

public record TeamsResponse(
        List<TeamInfoResponse> teams
) {
}
