package net.effize.bandlog.domain.user.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.Random;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "supabase_user_id"))
    private SupabaseUserId supabaseUserId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "email"))
    private Email email;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "nickname"))
    private Nickname nickname;

    @CreatedDate
    @Column(name = "created_at")
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt;

    protected User() {
    }

    private User(Long id, SupabaseUserId supabaseId, Email email, Nickname nickname, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.supabaseUserId = supabaseId;
        this.email = email;
        this.nickname = nickname;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static User create(SupabaseUserId supabaseId, Email email, Instant now, Random random) {
        return new User(null, supabaseId, email, Nickname.randomNickname(random, now), null, null);
    }

    public UserId id() {
        return UserId.of(id);
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
