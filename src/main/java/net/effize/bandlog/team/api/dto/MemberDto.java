package net.effize.bandlog.team.api.dto;

public record MemberDto(
        Long memberId,
        Long teamId,
        Long userId,
        MemberRoleDto role
) {
    public static MemberDto of(Long memberId, Long teamId, Long userId, MemberRoleDto role) {
        return new MemberDto(memberId, teamId, userId, role);
    }
}