package net.effize.bandlog.application.auth;

import net.effize.bandlog.application.auth.dto.AuthUser;
import net.effize.bandlog.application.auth.dto.AuthenticationPrincipal;
import net.effize.bandlog.application.auth.exception.IllegalAuthenticationException;
import net.effize.bandlog.application.auth.exception.UserNotSignedUpException;
import net.effize.bandlog.domain.user.model.User;
import net.effize.bandlog.infrastructure.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

@Service
public class AuthApplicationService {
    private final UserRepository userRepository;

    public AuthApplicationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void signup(Authentication authentication) {
        if (!(authentication.getPrincipal() instanceof AuthenticationPrincipal authenticationPrincipal)) {
            throw new IllegalAuthenticationException();
        }

        User newUser = User.create(
                authenticationPrincipal.supabaseUserId(),
                authenticationPrincipal.email(),
                Instant.now(),
                new Random()
        );

        userRepository.save(newUser);
    }

    public AuthUser authenticate(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalAuthenticationException();
        }

        if (!(authentication.getPrincipal() instanceof AuthenticationPrincipal authPrincipal)) {
            throw new IllegalAuthenticationException();
        }

        User user = userRepository.findBySupabaseUserId(authPrincipal.supabaseUserId())
                .orElseThrow(() -> new UserNotSignedUpException());

        return new AuthUser(user);
    }
}
