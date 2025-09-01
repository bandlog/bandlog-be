package net.effize.bandlog.team.application.dto;

import net.effize.bandlog.team.api.dto.MemberRoleDto;

import java.util.List;

public record TeamInfoResponse(
        String name,
        String description,
        String inviteCode,
        List<MemberInfo> members
) {
    public record MemberInfo(
            String nickname,
            MemberRoleDto role
    ) {

    }
}
