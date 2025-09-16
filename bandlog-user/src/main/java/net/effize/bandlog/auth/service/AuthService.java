package net.effize.bandlog.auth.service;

import net.effize.bandlog.auth.exception.IllegalAuthenticationException;
import net.effize.bandlog.auth.exception.UserNotSignedUpException;
import net.effize.bandlog.auth.model.SupabaseAuthenticationPrincipal;
import net.effize.bandlog.shared.auth.AuthUser;
import net.effize.bandlog.user.dto.response.UserResponse;
import net.effize.bandlog.user.model.Email;
import net.effize.bandlog.user.model.SupabaseUserId;
import net.effize.bandlog.user.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    public void signup(Authentication authentication) {
        if (!(authentication.getPrincipal() instanceof SupabaseAuthenticationPrincipal(
                String supabaseUserId, String email
        ))) {
            throw new IllegalAuthenticationException();
        }

        userService.signUp(new SupabaseUserId(supabaseUserId), new Email(email));
    }

    @Transactional(readOnly = true)
    public AuthUser authenticate(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalAuthenticationException();
        }

        if (!(authentication.getPrincipal() instanceof SupabaseAuthenticationPrincipal authPrincipal)) {
            throw new IllegalAuthenticationException();
        }

        UserResponse user = userService.findBySupabaseUserId(new SupabaseUserId(authPrincipal.supabaseUserId()))
                .orElseThrow(UserNotSignedUpException::new);

        return new AuthUser(
                user.id(),
                user.supabaseUserId(),
                user.email(),
                user.nickname()
        );
    }
}
