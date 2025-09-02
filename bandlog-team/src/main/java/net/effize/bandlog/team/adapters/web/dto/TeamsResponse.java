package net.effize.bandlog.team.adapters.web.dto;

import java.time.Instant;
import java.util.List;

public record TeamsResponse(
    List<TeamSummary> teams
) {
    public record TeamSummary(
        Long teamId,
        String name,
        String description,
        String memberRole,
        int memberCount,
        Instant joinedAt
    ) {
    }
}