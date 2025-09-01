package net.effize.bandlog.domain.team.model;

public record TeamId(Long value) {

    public static TeamId of(Long value) {
        return new TeamId(value);
    }
}
