package net.effize.bandlog.application.auth.dto;

import net.effize.bandlog.domain.user.model.User;

public record AuthUser(
        Long id,
        String supabaseUserId,
        String email,
        String nickname
) {
    public AuthUser(User user) {
        this(user.id().longValue(), user.supabaseUserId().toString(), user.email().toString(), user.nickname().toString());
    }
}