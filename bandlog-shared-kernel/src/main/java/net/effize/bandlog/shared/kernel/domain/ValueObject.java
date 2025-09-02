package net.effize.bandlog.shared.kernel.domain;

import java.io.Serializable;

/**
 * Base interface for Value Objects in Domain-Driven Design.
 * Value Objects are immutable and are defined by their attributes.
 * 
 * Based on Vaughn Vernon's DDD principles.
 */
public interface ValueObject extends Serializable {
    
    /**
     * Value objects must implement proper equals based on their attributes
     */
    @Override
    boolean equals(Object other);
    
    /**
     * Value objects must implement proper hashCode based on their attributes
     */
    @Override
    int hashCode();
}