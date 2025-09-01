package net.effize.bandlog.identity.api;

import net.effize.bandlog.identity.api.dto.UserDto;

import java.util.Collection;
import java.util.List;

public interface UserService {
    UserDto findBySupabaseUserId(String supabaseUserId);
    boolean existsBySupabaseUserId(String supabaseUserId);
    UserDto createUser(String supabaseUserId, String email);
    
    List<UserDto> findAllByIdIn(Collection<Long> userIds);
}