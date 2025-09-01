package net.effize.bandlog.auth.application;

import net.effize.bandlog.auth.application.dto.AuthUser;
import net.effize.bandlog.auth.application.dto.AuthenticationPrincipal;
import net.effize.bandlog.auth.application.exception.IllegalAuthenticationException;
import net.effize.bandlog.auth.application.exception.UserNotSignedUpException;
import net.effize.bandlog.identity.api.UserService;
import net.effize.bandlog.identity.api.dto.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AuthApplicationService {
    private final UserService userService;

    public AuthApplicationService(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    public void signup(Authentication authentication) {
        if (!(authentication.getPrincipal() instanceof AuthenticationPrincipal authenticationPrincipal)) {
            throw new IllegalAuthenticationException();
        }

        if (userService.existsBySupabaseUserId(authenticationPrincipal.supabaseUserId())) {
            return;
        }

        userService.createUser(
                authenticationPrincipal.supabaseUserId(),
                authenticationPrincipal.email()
        );
    }

    @Transactional(readOnly = true)
    public AuthUser authenticate(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalAuthenticationException();
        }

        if (!(authentication.getPrincipal() instanceof AuthenticationPrincipal authPrincipal)) {
            throw new IllegalAuthenticationException();
        }

        UserDto user = userService.findBySupabaseUserId(authPrincipal.supabaseUserId());

        return new AuthUser(user);
    }
}
