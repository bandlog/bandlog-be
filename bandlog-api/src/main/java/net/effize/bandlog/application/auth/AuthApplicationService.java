package net.effize.bandlog.application.auth;

import net.effize.bandlog.api.auth.exception.IllegalAuthenticationException;
import net.effize.bandlog.api.auth.exception.UserNotSignedUpException;
import net.effize.bandlog.application.auth.dto.AuthUser;
import net.effize.bandlog.application.auth.dto.AuthenticationPrincipal;
import net.effize.bandlog.domain.user.model.User;
import net.effize.bandlog.domain.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthApplicationService {
    private final UserRepository userRepository;

    public AuthApplicationService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
