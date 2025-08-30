package net.effize.bandlog.api.config.converter;

import net.effize.bandlog.application.auth.dto.AuthenticationPrincipal;
import net.effize.bandlog.domain.user.model.Email;
import net.effize.bandlog.domain.user.model.SupabaseUserId;
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

        SupabaseUserId supabaseUserId = new SupabaseUserId(supabaseIdString);
        Email email = new Email(emailString);

        AuthenticationPrincipal principal = new AuthenticationPrincipal(supabaseUserId, email);
        Collection<? extends GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_REGISTRATION"));

        return UsernamePasswordAuthenticationToken.authenticated(principal, null, authorities);
    }
}