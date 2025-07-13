package net.effize.bandlog.domain.user.model;

import java.time.Instant;

public class User {
    private UserId id;

    private Email email;

    private RawPassword password;

    private Instant createdAt;
    private Instant updatedAt;
}
