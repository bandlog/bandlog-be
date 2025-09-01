package net.effize.bandlog.team.api.events;

import java.time.Instant;

public record TeamCreatedEvent(
    Long teamId,
    String name,
    String description,
    Instant createdAt
) {
}