package net.effize.bandlog.user.domain.model;

import jakarta.persistence.Embeddable;
import net.effize.bandlog.shared.kernel.domain.ValueObject;

import java.util.Objects;

@Embeddable
public record SupabaseUserId(String value) implements ValueObject {
    
    public SupabaseUserId {
        Objects.requireNonNull(value, "SupabaseUserId cannot be null");
        if (value.isBlank()) {
            throw new IllegalArgumentException("SupabaseUserId cannot be blank");
        }
    }
    
    public static SupabaseUserId of(String value) {
        return new SupabaseUserId(value);
    }
    
    @Override
    public String toString() {
        return value;
    }
}