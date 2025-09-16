package net.effize.bandlog.team.model;

public record TeamId(Long value) {

    public static TeamId of(Long value) {
        return new TeamId(value);
    }
}
