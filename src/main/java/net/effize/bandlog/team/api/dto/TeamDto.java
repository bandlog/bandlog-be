package net.effize.bandlog.team.api.dto;

import java.time.Instant;

public record TeamDto(
        Long id,
        String name,
        String description,
        String inviteCode,
        Instant createdAt
) {
    public static TeamDto of(Long id, String name, String description, String inviteCode, Instant createdAt) {
        return new TeamDto(id, name, description, inviteCode, createdAt);
    }
}