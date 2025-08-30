package net.effize.bandlog.domain.team.model;

import java.util.Objects;

public final class TeamId {
    private final Long value;

    private TeamId(Long value) {
        this.value = value;
    }

    public static TeamId of(Long value) {
        return new TeamId(value);
    }

    public Long longValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TeamId teamId)) return false;
        return Objects.equals(value, teamId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
