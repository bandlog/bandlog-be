package net.effize.bandlog.team.api.dto;

public record TeamIdDto(Long value) {
    public static TeamIdDto of(Long value) {
        return new TeamIdDto(value);
    }
}