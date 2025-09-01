package net.effize.bandlog.domain.team.model;

import jakarta.persistence.*;
import net.effize.bandlog.domain.user.model.UserId;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Table(name = "members")
@EntityListeners(AuditingEntityListener.class)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "user_id", nullable = false))
    private UserId userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private MemberRole role;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
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
