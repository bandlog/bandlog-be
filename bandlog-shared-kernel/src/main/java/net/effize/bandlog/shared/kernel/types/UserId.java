package net.effize.bandlog.shared.kernel.types;

import net.effize.bandlog.shared.kernel.domain.ValueObject;

import java.util.Objects;

/**
 * User identifier Value Object.
 * This is part of the Shared Kernel as it's used across multiple Bounded Contexts.
 */
public record UserId(Long value) implements ValueObject {
    
    public UserId {
        Objects.requireNonNull(value, "UserId value cannot be null");
        if (value <= 0) {
            throw new IllegalArgumentException("UserId must be positive");
        }
    }
    
    public static UserId of(Long value) {
        return new UserId(value);
    }
    
    @Override
    public String toString() {
        return value.toString();
    }
}