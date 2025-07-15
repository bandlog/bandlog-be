package net.effize.bandlog.domain.user.model;

import java.time.Instant;

public class User {
    private UserId id;

    private SupabaseUserId supabaseId;

    private Email email;

    private Nickname nickname;

    private Instant createdAt;
    private Instant updatedAt;
}
