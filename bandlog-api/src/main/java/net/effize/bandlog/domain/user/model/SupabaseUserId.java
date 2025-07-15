package net.effize.bandlog.domain.user.model;

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
}
