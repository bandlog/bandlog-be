package net.effize.bandlog.identity.domain.model;

import jakarta.persistence.Embeddable;

@Embeddable
public record SupabaseUserId(
        String value
) {
}

