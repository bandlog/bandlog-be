package net.effize.bandlog.domain.user.model;

import java.time.Instant;
import java.util.Random;

public class User {
    private UserId id;

    private SupabaseUserId supabaseUserId;

    private Email email;

    private Nickname nickname;

    private Instant createdAt;

    private Instant updatedAt;

    private User(UserId id, SupabaseUserId supabaseId, Email email, Nickname nickname, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.supabaseUserId = supabaseId;
        this.email = email;
        this.nickname = nickname;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static User create(SupabaseUserId supabaseId, Email email, Instant now, Random random) {
        return new User(null, supabaseId, email, Nickname.randomNickname(random, now), now, now);
    }

    public UserId id() {
        return id;
    }

    public SupabaseUserId supabaseUserId() {
        return supabaseUserId;
    }

    public Email email() {
        return email;
    }

    public Nickname nickname() {
        return nickname;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }
}
