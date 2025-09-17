package net.effize.bandlog.team.dto.response;


import net.effize.bandlog.team.model.MemberRole;

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
