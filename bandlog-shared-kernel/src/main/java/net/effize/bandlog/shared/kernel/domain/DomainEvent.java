package net.effize.bandlog.shared.kernel.domain;

import java.time.Instant;

/**
 * Base interface for Domain Events.
 * Domain Events capture something that happened in the domain.
 * 
 * Based on Vaughn Vernon's DDD principles.
 */
public interface DomainEvent {
    
    /**
     * When the event occurred
     */
    Instant occurredOn();
    
    /**
     * The version of the event for schema evolution
     */
    default int eventVersion() {
        return 1;
    }
}