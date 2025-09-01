package net.effize.bandlog.identity.api.dto;

import java.time.Instant;

public record UserDto(
        Long id,
        String supabaseUserId,
        String email,
        String nickname,
        Instant createdAt,
        Instant updatedAt
) {
    public static UserDto of(Long id, String supabaseUserId, String email, String nickname, Instant createdAt, Instant updatedAt) {
        return new UserDto(id, supabaseUserId, email, nickname, createdAt, updatedAt);
    }
}