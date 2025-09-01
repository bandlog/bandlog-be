package net.effize.bandlog.application.team.dto;

import net.effize.bandlog.domain.team.model.MemberRole;

import java.util.List;

public record TeamInfoResponse(
        String name,
        String description,
        String inviteCode,
        List<MemberInfo> members
) {
    public record MemberInfo(
            String nickname,
            MemberRole role
    ) {

    }
}
