package net.effize.bandlog.domain.user.model;

public class SupabaseUserId {
    private final Long value;

    private SupabaseUserId(Long value) {
        this.value = value;
    }

    public static SupabaseUserId of(Long value) {
        return new SupabaseUserId(value);
    }

    public Long longValue() {
        return value;
    }
}
