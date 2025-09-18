package net.effize.bandlog.auth.service;

import net.effize.bandlog.auth.exception.IllegalAuthenticationException;
import net.effize.bandlog.auth.exception.UserNotSignedUpException;
import net.effize.bandlog.auth.model.SupabaseAuthenticationPrincipal;
import net.effize.bandlog.port.user.BandlogAuthUserPort;
import net.effize.bandlog.port.user.dto.BandlogUserResponse;
import net.effize.bandlog.shared.auth.AuthUser;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final BandlogAuthUserPort authUserPort;

    public AuthService(BandlogAuthUserPort authUserPort) {
        this.authUserPort = authUserPort;
    }

    @Transactional
    public void signup(Authentication authentication) {
        if (!(authentication.getPrincipal() instanceof SupabaseAuthenticationPrincipal(
                String supabaseUserId, String email
        ))) {
            throw new IllegalAuthenticationException();
        }

        authUserPort.signUp(supabaseUserId, email);
    }

    @Transactional(readOnly = true)
    public AuthUser authenticate(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalAuthenticationException();
        }

        if (!(authentication.getPrincipal() instanceof SupabaseAuthenticationPrincipal authPrincipal)) {
            throw new IllegalAuthenticationException();
        }

        BandlogUserResponse userResponse = authUserPort.findBySupabaseUserId(authPrincipal.supabaseUserId())
                .orElseThrow(UserNotSignedUpException::new);

        return new AuthUser(
                userResponse.id(),
                userResponse.supabaseUserId(),
                userResponse.email(),
                userResponse.nickname()
        );
    }
}
