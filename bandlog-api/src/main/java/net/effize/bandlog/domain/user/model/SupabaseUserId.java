package net.effize.bandlog.domain.user.model;

import java.util.Objects;

public class SupabaseUserId {
    private final String value;

    private SupabaseUserId(String value) {
        this.value = value;
    }

    public static SupabaseUserId of(String value) {
        return new SupabaseUserId(value);
    }

    public String stringValue() {
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
