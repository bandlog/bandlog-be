package net.effize.bandlog.team.domain.model;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "teams")
@EntityListeners(AuditingEntityListener.class)
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "invite_code")
    private String inviteCode;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    protected Team() {
    }

    private Team(Long id, String name, String description, String inviteCode, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.inviteCode = inviteCode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Team create(String name, String description, Instant now) {
        return new Team(null, name, description, generateInviteCode(), now, now);
    }

    private static String generateInviteCode() {
        UUID inviteCode = UUID.randomUUID();
        return inviteCode.toString();
    }

    public TeamId id() {
        return TeamId.of(id);
    }

    public void refreshInviteCode() {
        this.inviteCode = generateInviteCode();
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public String inviteCode() {
        return inviteCode;
    }

    public Instant createdAt() {
        return createdAt;
    }
}
