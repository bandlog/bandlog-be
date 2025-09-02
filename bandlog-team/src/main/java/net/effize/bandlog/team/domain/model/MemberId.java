package net.effize.bandlog.team.domain.model;

import net.effize.bandlog.shared.kernel.domain.ValueObject;

import java.util.Objects;

public record MemberId(Long value) implements ValueObject {
    
    public MemberId {
        Objects.requireNonNull(value, "MemberId value cannot be null");
        if (value <= 0) {
            throw new IllegalArgumentException("MemberId must be positive");
        }
    }
    
    public static MemberId of(Long value) {
        return new MemberId(value);
    }
    
    @Override
    public String toString() {
        return value.toString();
    }
}