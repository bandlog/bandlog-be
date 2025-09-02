package net.effize.bandlog.team.domain.event;

import net.effize.bandlog.shared.kernel.domain.DomainEvent;
import net.effize.bandlog.shared.kernel.types.TeamId;
import net.effize.bandlog.shared.kernel.types.UserId;

import java.time.Instant;

public record TeamCreated(
    TeamId teamId,
    String teamName,
    UserId creatorUserId,
    Instant occurredOn
) implements DomainEvent {
    
    @Override
    public int eventVersion() {
        return 1;
    }
}