package net.effize.bandlog.api.config.converter;

import net.effize.bandlog.auth.model.SupabaseAuthenticationPrincipal;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class JwtToAuthenticationPrincipalConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        String supabaseIdString = jwt.getSubject();
        String emailString = jwt.getClaimAsString("email");

        SupabaseAuthenticationPrincipal principal = new SupabaseAuthenticationPrincipal(supabaseIdString, emailString);
        Collection<? extends GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_REGISTRATION"));

        return UsernamePasswordAuthenticationToken.authenticated(principal, null, authorities);
    }
}