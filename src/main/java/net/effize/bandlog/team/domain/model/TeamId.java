package net.effize.bandlog.team.domain.model;

public record TeamId(Long value) {

    public static TeamId of(Long value) {
        return new TeamId(value);
    }
}
