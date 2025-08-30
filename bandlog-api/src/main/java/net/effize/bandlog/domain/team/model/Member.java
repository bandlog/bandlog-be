package net.effize.bandlog.domain.team.model;

import net.effize.bandlog.domain.user.model.UserId;

import java.time.Instant;

public class Member {
    private Long id;

    private Team team;

    private UserId userId;

    private MemberRole role;

    private Instant createdAt;

    private Instant updatedAt;

    protected Member() {
    }

    private Member(Long id, Team team, UserId userId, MemberRole role, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.team = team;
        this.userId = userId;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Member create(Team team, UserId userId, MemberRole role, Instant now) {
        return new Member(null, team, userId, role, now, now);
    }

    public MemberId id() {
        return MemberId.of(id);
    }

    public MemberRole role() {
        return role;
    }

    public UserId userId() {
        return userId;
    }
}
