package net.effize.bandlog.identity.application;

import net.effize.bandlog.identity.api.UserService;
import net.effize.bandlog.identity.api.dto.UserDto;
import net.effize.bandlog.identity.application.converter.UserConverter;
import net.effize.bandlog.identity.domain.model.*;
import net.effize.bandlog.identity.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@Service
public class UserApplicationService implements UserService {
    private static final Random RANDOM = new SecureRandom();
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public UserApplicationService(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Override
    public UserDto findBySupabaseUserId(String supabaseUserId) {
        SupabaseUserId supabaseUserIdVO = new SupabaseUserId(supabaseUserId);
        User user = userRepository.findBySupabaseUserId(supabaseUserIdVO)
                .orElseThrow(() -> new RuntimeException("User not signed up"));
        return userConverter.toDto(user);
    }

    @Override
    public boolean existsBySupabaseUserId(String supabaseUserId) {
        SupabaseUserId supabaseUserIdVO = new SupabaseUserId(supabaseUserId);
        return userRepository.existsBySupabaseUserId(supabaseUserIdVO);
    }

    @Override
    public UserDto createUser(String supabaseUserId, String email) {
        SupabaseUserId supabaseUserIdVO = new SupabaseUserId(supabaseUserId);
        Email emailVO = new Email(email);
        User user = User.create(supabaseUserIdVO, emailVO, Instant.now(), RANDOM);
        User savedUser = userRepository.save(user);
        return userConverter.toDto(savedUser);
    }
    
    @Override
    public List<UserDto> findAllByIdIn(Collection<Long> userIds) {
        List<User> users = userRepository.findAllByIdIn(userIds);
        return userConverter.toDto(users);
    }
}