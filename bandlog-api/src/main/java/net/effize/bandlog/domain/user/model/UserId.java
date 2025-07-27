package net.effize.bandlog.domain.user.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UserId userId)) return false;
        return Objects.equals(value, userId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
