package net.effize.bandlog.team.domain.event;

import net.effize.bandlog.shared.kernel.domain.DomainEvent;
import net.effize.bandlog.shared.kernel.types.TeamId;
import net.effize.bandlog.shared.kernel.types.UserId;
import net.effize.bandlog.team.domain.model.MemberRole;

import java.time.Instant;

public record MemberJoined(
    TeamId teamId,
    UserId userId,
    MemberRole role,
    Instant occurredOn
) implements DomainEvent {
    
    @Override
    public int eventVersion() {
        return 1;
    }
}