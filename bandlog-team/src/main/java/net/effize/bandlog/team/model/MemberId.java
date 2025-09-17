package net.effize.bandlog.team.model;

public record MemberId(Long value) {

    public static MemberId of(Long value) {
        return new MemberId(value);
    }
}
