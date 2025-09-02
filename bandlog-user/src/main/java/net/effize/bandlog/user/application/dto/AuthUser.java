package net.effize.bandlog.user.application.dto;

import net.effize.bandlog.shared.kernel.types.UserId;
import net.effize.bandlog.user.domain.model.*;

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