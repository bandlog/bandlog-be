package net.effize.bandlog.domain.team.model;

import java.util.Objects;

public final class MemberId {
    private final Long value;

    private MemberId(Long value) {
        this.value = value;
    }

    public static MemberId of(Long value) {
        return new MemberId(value);
    }

    public Long longValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MemberId memberId)) return false;
        return Objects.equals(value, memberId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
