package net.effize.bandlog.bootstrap.config.converter;

import net.effize.bandlog.user.application.dto.AuthenticationPrincipal;
import net.effize.bandlog.user.domain.model.Email;
import net.effize.bandlog.user.domain.model.SupabaseUserId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collections;

public class JwtToAuthenticationPrincipalConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        String supabaseUserId = jwt.getSubject();
        String email = jwt.getClaimAsString("email");

        AuthenticationPrincipal principal = new AuthenticationPrincipal(
                new SupabaseUserId(supabaseUserId),
                new Email(email)
        );

        return new AbstractAuthenticationToken(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))) {
            @Override
            public Object getCredentials() {
                return jwt.getTokenValue();
            }

            @Override
            public Object getPrincipal() {
                return principal;
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }
        };
    }
}