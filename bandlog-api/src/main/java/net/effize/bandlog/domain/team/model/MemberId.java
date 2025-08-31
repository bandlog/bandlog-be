package net.effize.bandlog.domain.team.model;

public record MemberId(Long value) {

    public static MemberId of(Long value) {
        return new MemberId(value);
    }
}
