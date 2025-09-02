package net.effize.bandlog.team.domain.model;

import jakarta.persistence.*;
import net.effize.bandlog.shared.kernel.domain.AggregateRoot;
import net.effize.bandlog.shared.kernel.types.TeamId;
import net.effize.bandlog.shared.kernel.types.UserId;
import net.effize.bandlog.team.domain.event.MemberJoined;
import net.effize.bandlog.team.domain.event.TeamCreated;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "teams")
@EntityListeners(AuditingEntityListener.class)
public class Team extends AggregateRoot<TeamId> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "invite_code")
    private String inviteCode;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Member> members = new HashSet<>();

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

    public static Team create(String name, String description, UserId creatorUserId, Instant now) {
        Team team = new Team(null, name, description, generateInviteCode(), now, now);
        
        // Add creator as owner
        Member creator = Member.create(team, creatorUserId, MemberRole.OWNER, now);
        team.members.add(creator);
        
        // Register domain event
        team.registerEvent(new TeamCreated(null, name, creatorUserId, now));
        
        return team;
    }

    private static String generateInviteCode() {
        return UUID.randomUUID().toString();
    }

    public void addMember(UserId userId, MemberRole role, Instant now) {
        // Check invariants
        if (members.stream().anyMatch(m -> m.userId().equals(userId))) {
            throw new IllegalArgumentException("User is already a member");
        }
        
        Member member = Member.create(this, userId, role, now);
        members.add(member);
        
        registerEvent(new MemberJoined(this.id(), userId, role, now));
    }

    @Override
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

    public Set<Member> members() {
        return new HashSet<>(members);
    }

    public Instant createdAt() {
        return createdAt;
    }

    public boolean isOwnerOrAdmin(UserId userId) {
        return members.stream()
            .anyMatch(member -> member.userId().equals(userId) && 
                      (member.role() == MemberRole.OWNER || member.role() == MemberRole.ADMIN));
    }

    public boolean isMember(UserId userId) {
        return members.stream()
            .anyMatch(member -> member.userId().equals(userId));
    }
}