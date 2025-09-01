package net.effize.bandlog.team.domain.model;

public record MemberId(Long value) {

    public static MemberId of(Long value) {
        return new MemberId(value);
    }
}
