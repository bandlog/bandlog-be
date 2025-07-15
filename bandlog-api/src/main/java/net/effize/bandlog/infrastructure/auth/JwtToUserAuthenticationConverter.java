package net.effize.bandlog.infrastructure.auth;

import net.effize.bandlog.domain.user.model.Email;
import net.effize.bandlog.domain.user.model.SupabaseUserId;
import net.effize.bandlog.domain.user.model.User;
import net.effize.bandlog.domain.user.repository.UserRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Random;

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
                .orElseGet(() -> {
                    String emailString = jwt.getClaimAsString("email");
                    Email email = Email.of(emailString);

                    User newUser = User.create(supabaseUserId, email, Instant.now(), new Random());
                    return userRepository.save(newUser);
                });

        Collection<? extends GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

        return UsernamePasswordAuthenticationToken.authenticated(user, null, authorities);
    }
}
