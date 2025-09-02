package net.effize.bandlog.shared.kernel.domain;

import org.springframework.data.annotation.Transient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Base class for Aggregate Roots in Domain-Driven Design.
 * Aggregate Roots are the entry points to aggregates and maintain consistency boundaries.
 * 
 * Based on Vaughn Vernon's DDD principles.
 * 
 * @param <ID> The type of the aggregate's identifier
 */
public abstract class AggregateRoot<ID> {
    
    @Transient
    private final transient List<DomainEvent> domainEvents = new ArrayList<>();
    
    /**
     * Get the unique identifier of this aggregate
     */
    public abstract ID id();
    
    /**
     * Register a domain event to be published
     */
    protected void registerEvent(DomainEvent event) {
        domainEvents.add(event);
    }
    
    /**
     * Clear all domain events (typically called after publishing)
     */
    public void clearEvents() {
        domainEvents.clear();
    }
    
    /**
     * Get all unpublished domain events
     */
    public List<DomainEvent> getEvents() {
        return Collections.unmodifiableList(domainEvents);
    }
}