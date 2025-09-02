package net.effize.bandlog.team.adapters.web.dto;

import java.time.Instant;
import java.util.List;

public record TeamInfoResponse(
    Long teamId,
    String name,
    String description,
    String inviteCode,
    Instant createdAt,
    List<MemberInfo> members
) {
    public record MemberInfo(
        Long userId,
        String role,
        Instant joinedAt
    ) {
    }
}