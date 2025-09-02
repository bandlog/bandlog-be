package net.effize.bandlog.shared.kernel.types;

import net.effize.bandlog.shared.kernel.domain.ValueObject;

import java.util.Objects;

/**
 * Team identifier Value Object.
 * This is part of the Shared Kernel as it's used across multiple Bounded Contexts.
 */
public record TeamId(Long value) implements ValueObject {
    
    public TeamId {
        Objects.requireNonNull(value, "TeamId value cannot be null");
        if (value <= 0) {
            throw new IllegalArgumentException("TeamId must be positive");
        }
    }
    
    public static TeamId of(Long value) {
        return new TeamId(value);
    }
    
    @Override
    public String toString() {
        return value.toString();
    }
}