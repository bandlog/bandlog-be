package net.effize.bandlog.domain.user;

import jakarta.persistence.Embeddable;

@Embeddable
public record SupabaseUserId(
        String value
) {
}

