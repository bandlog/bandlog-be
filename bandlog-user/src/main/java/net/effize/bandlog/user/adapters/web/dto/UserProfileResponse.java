package net.effize.bandlog.user.adapters.web.dto;

import java.time.Instant;

public record UserProfileResponse(
    Long id,
    String email,
    String nickname,
    Instant createdAt
) {
}