package net.effize.bandlog.identity.application.converter;

import net.effize.bandlog.identity.api.dto.EmailDto;
import net.effize.bandlog.identity.api.dto.SupabaseUserIdDto;
import net.effize.bandlog.identity.api.dto.UserDto;
import net.effize.bandlog.identity.api.dto.UserIdDto;
import net.effize.bandlog.identity.domain.model.Email;
import net.effize.bandlog.identity.domain.model.SupabaseUserId;
import net.effize.bandlog.identity.domain.model.User;
import net.effize.bandlog.identity.domain.model.UserId;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserConverter {
    
    public UserDto toDto(User domain) {
        return UserDto.of(
            domain.id().value(),
            domain.supabaseUserId().value(),
            domain.email().value(),
            domain.nickname().value(),
            domain.createdAt(),
            domain.updatedAt()
        );
    }
    
    public List<UserDto> toDto(List<User> domains) {
        return domains.stream()
                .map(this::toDto)
                .toList();
    }
    
    public UserIdDto toDto(UserId domain) {
        return UserIdDto.of(domain.value());
    }
    
    public SupabaseUserIdDto toDto(SupabaseUserId domain) {
        return new SupabaseUserIdDto(domain.value());
    }
    
    public EmailDto toDto(Email domain) {
        return new EmailDto(domain.value());
    }
}