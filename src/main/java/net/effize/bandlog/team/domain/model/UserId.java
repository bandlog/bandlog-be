package net.effize.bandlog.team.domain.model;

import jakarta.persistence.Embeddable;

@Embeddable
public record UserId(Long value) {
    
    public static UserId of(Long value) {
        return new UserId(value);
    }
}