package net.effize.bandlog.application.auth.dto;

import net.effize.bandlog.domain.user.model.*;

public record AuthUser(
        UserId id,
        SupabaseUserId supabaseUserId,
        Email email,
        Nickname nickname
) {
    public AuthUser(User user) {
        this(user.id(), user.supabaseUserId(), user.email(), user.nickname());
    }
}