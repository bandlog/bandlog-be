package net.effize.bandlog.api.config;

import net.effize.bandlog.api.config.converter.JwtToAuthenticationPrincipalConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtToAuthenticationPrincipalConverter jwtToAuthenticationPrincipalConverter;

    public SecurityConfig(JwtToAuthenticationPrincipalConverter jwtToAuthenticationPrincipalConverter) {
        this.jwtToAuthenticationPrincipalConverter = jwtToAuthenticationPrincipalConverter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/public/**", "/health/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtToAuthenticationPrincipalConverter)
                        )
                );

        return http.build();
    }
}
