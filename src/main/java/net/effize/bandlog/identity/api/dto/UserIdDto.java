package net.effize.bandlog.identity.api.dto;

public record UserIdDto(Long value) {
    public static UserIdDto of(Long value) {
        return new UserIdDto(value);
    }
}