package net.effize.bandlog.team.api.events;

import java.time.Instant;

public record MemberJoinedEvent(
    Long teamId,
    Long userId,
    String role,
    Instant joinedAt
) {
}