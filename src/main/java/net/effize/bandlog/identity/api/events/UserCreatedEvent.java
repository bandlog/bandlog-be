package net.effize.bandlog.identity.api.events;

import java.time.Instant;

public record UserCreatedEvent(
    Long userId,
    String email,
    String nickname,
    Instant createdAt
) {
}