package net.effize.bandlog.user.model;

import jakarta.persistence.Embeddable;

@Embeddable
public record SupabaseUserId(
        String value
) {
}

