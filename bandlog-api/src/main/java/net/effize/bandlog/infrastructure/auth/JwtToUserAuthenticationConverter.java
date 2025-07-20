package net.effize.bandlog.infrastructure.auth;

import net.effize.bandlog.domain.user.model.SupabaseUserId;
import net.effize.bandlog.domain.user.model.User;
import net.effize.bandlog.domain.user.repository.UserRepository;
import net.effize.bandlog.infrastructure.auth.exception.UserNotSignedUpException;
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
public class JwtToUserAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final UserRepository userRepository;

    public JwtToUserAuthenticationConverter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        String supabaseIdString = jwt.getSubject();
        SupabaseUserId supabaseUserId = SupabaseUserId.of(supabaseIdString);

        User user = userRepository.findBySupabaseUserId(supabaseUserId)
                .orElseThrow(() -> new UserNotSignedUpException());

        Collection<? extends GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

        return UsernamePasswordAuthenticationToken.authenticated(user, null, authorities);
    }
}
