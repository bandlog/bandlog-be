package net.effize.bandlog.shared.kernel.event;

import net.effize.bandlog.shared.kernel.domain.DomainEvent;
import net.effize.bandlog.shared.kernel.types.UserId;

import java.time.Instant;

/**
 * Event published when a new user is registered.
 * This is part of the Shared Kernel as it's used for cross-context communication.
 */
public record UserRegisteredEvent(
    UserId userId,
    String email,
    String nickname,
    Instant occurredOn
) implements DomainEvent {
    
    @Override
    public int eventVersion() {
        return 1;
    }
}