package net.effize.bandlog.auth.api;

import net.effize.bandlog.identity.api.dto.UserDto;

public record AuthUser(
        Long id,
        String supabaseUserId,
        String email,
        String nickname
) {
    public AuthUser(UserDto user) {
        this(user.id(), user.supabaseUserId(), user.email(), user.nickname());
    }
}