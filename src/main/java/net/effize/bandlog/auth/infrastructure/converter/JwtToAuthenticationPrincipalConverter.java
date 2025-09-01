package net.effize.bandlog.auth.infrastructure.converter;

import net.effize.bandlog.auth.api.AuthenticationPrincipal;
import net.effize.bandlog.auth.api.JwtAuthenticationConverter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class JwtToAuthenticationPrincipalConverter implements JwtAuthenticationConverter {

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        String supabaseIdString = jwt.getSubject();
        String emailString = jwt.getClaimAsString("email");

        AuthenticationPrincipal principal = new AuthenticationPrincipal(supabaseIdString, emailString);
        Collection<? extends GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_REGISTRATION"));

        return UsernamePasswordAuthenticationToken.authenticated(principal, null, authorities);
    }
}