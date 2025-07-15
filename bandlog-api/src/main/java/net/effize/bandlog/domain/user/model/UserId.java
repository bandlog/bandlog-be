package net.effize.bandlog.domain.user.model;

public class UserId {
    private final Long value;

    private UserId(Long value) {
        this.value = value;
    }

    public static UserId of(Long value) {
        return new UserId(value);
    }

    public Long longValue() {
        return value;
    }
}
