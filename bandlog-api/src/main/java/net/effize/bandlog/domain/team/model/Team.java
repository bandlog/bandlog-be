package net.effize.bandlog.domain.team.model;

import java.time.Instant;

public class Team {
    private Long id;

    private String name;

    private String description;

    private String inviteCode;

    private Instant createdAt;

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
        return new Team(null, name, description, null, now, now);
    }

    public TeamId id() {
        return TeamId.of(id);
    }
}
