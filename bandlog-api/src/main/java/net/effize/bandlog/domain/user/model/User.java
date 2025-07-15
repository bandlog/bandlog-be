package net.effize.bandlog.domain.user.model;

import java.time.Instant;
import java.util.Random;

public class User {
    private UserId id;

    private SupabaseUserId supabaseId;

    private Email email;

    private Nickname nickname;

    private Instant createdAt;

    private Instant updatedAt;

    private User(UserId id, SupabaseUserId supabaseId, Email email, Nickname nickname, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.supabaseId = supabaseId;
        this.email = email;
        this.nickname = nickname;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static User create(SupabaseUserId supabaseId, Email email, Instant now, Random random) {
        return new User(null, supabaseId, email, Nickname.randomNickname(random, now), now, now);
    }
}
