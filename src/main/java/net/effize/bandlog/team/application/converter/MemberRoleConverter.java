package net.effize.bandlog.team.application.converter;

import net.effize.bandlog.team.api.dto.MemberRoleDto;
import net.effize.bandlog.team.domain.model.MemberRole;
import org.springframework.stereotype.Component;

@Component
public class MemberRoleConverter {
    
    public MemberRoleDto toDto(MemberRole domain) {
        return switch (domain) {
            case LEADER -> MemberRoleDto.LEADER;
            case MANAGER -> MemberRoleDto.MANAGER;
            case MEMBER -> MemberRoleDto.MEMBER;
        };
    }
    
    public MemberRole toDomain(MemberRoleDto dto) {
        return switch (dto) {
            case LEADER -> MemberRole.LEADER;
            case MANAGER -> MemberRole.MANAGER;
            case MEMBER -> MemberRole.MEMBER;
        };
    }
}