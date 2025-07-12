package net.effize.bandlog.domain.user.model;

import java.time.Instant;

public class User {
    private Long id;

    private Email email;

    private Password password;

    private Instant createdAt;
    private Instant updatedAt;
}
