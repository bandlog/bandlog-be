package net.effize.bandlog.domain.user.model;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class SupabaseUserId {
    private String value;

    protected SupabaseUserId() {
    }

    private SupabaseUserId(String value) {
        this.value = value;
    }

    public static SupabaseUserId of(String value) {
        return new SupabaseUserId(value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SupabaseUserId that = (SupabaseUserId) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
