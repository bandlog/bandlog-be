package net.effize.bandlog.auth.service;

import net.effize.bandlog.auth.exception.IllegalAuthenticationException;
import net.effize.bandlog.auth.exception.UserNotSignedUpException;
import net.effize.bandlog.auth.model.SupabaseAuthenticationPrincipal;
import net.effize.bandlog.shared.auth.AuthUser;
import net.effize.bandlog.user.model.Email;
import net.effize.bandlog.user.model.SupabaseUserId;
import net.effize.bandlog.user.model.User;
import net.effize.bandlog.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Random;

@Service
public class AuthService {
    private static final Random RANDOM = new SecureRandom();
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void signup(Authentication authentication) {
        if (!(authentication.getPrincipal() instanceof SupabaseAuthenticationPrincipal(
                String supabaseUserId, String email
        ))) {
            throw new IllegalAuthenticationException();
        }

        if (userRepository.existsBySupabaseUserId(new SupabaseUserId(supabaseUserId))) {
            return;
        }

        User newUser = User.create(
                new SupabaseUserId(supabaseUserId),
                new Email(email),
                Instant.now(),
                RANDOM
        );

        userRepository.save(newUser);
    }

    @Transactional(readOnly = true)
    public AuthUser authenticate(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalAuthenticationException();
        }

        if (!(authentication.getPrincipal() instanceof SupabaseAuthenticationPrincipal authPrincipal)) {
            throw new IllegalAuthenticationException();
        }

        User user = userRepository.findBySupabaseUserId(new SupabaseUserId(authPrincipal.supabaseUserId()))
                .orElseThrow(UserNotSignedUpException::new);

        return new AuthUser(
                user.id().value(),
                user.supabaseUserId().value(),
                user.email().value(),
                user.nickname().value()
        );
    }
}
