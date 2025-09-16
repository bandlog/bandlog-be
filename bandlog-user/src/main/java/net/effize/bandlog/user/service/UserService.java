package net.effize.bandlog.user.service;

import net.effize.bandlog.user.dto.response.UserResponse;
import net.effize.bandlog.user.model.Email;
import net.effize.bandlog.user.model.SupabaseUserId;
import net.effize.bandlog.user.model.User;
import net.effize.bandlog.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {
    private static final Random RANDOM = new SecureRandom();

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void signUp(SupabaseUserId supabaseUserId, Email email) {
        if (userRepository.existsBySupabaseUserId(supabaseUserId)) {
            return;
        }

        User newUser = User.create(
                supabaseUserId,
                email,
                Instant.now(),
                RANDOM
        );

        userRepository.save(newUser);
    }

    public Optional<UserResponse> findBySupabaseUserId(SupabaseUserId supabaseUserId) {
        return userRepository.findBySupabaseUserId(supabaseUserId)
                .map(user -> new UserResponse(
                        user.id().value(),
                        user.supabaseUserId().value(),
                        user.email().value(),
                        user.nickname().value()
                ));
    }
}
