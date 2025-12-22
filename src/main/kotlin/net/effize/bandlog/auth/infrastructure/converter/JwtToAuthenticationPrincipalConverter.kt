package net.effize.bandlog.auth.infrastructure.converter

import net.effize.bandlog.auth.domain.SupabaseAuthenticationPrincipal
import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Component

@Component
class JwtToAuthenticationPrincipalConverter : Converter<Jwt, AbstractAuthenticationToken> {

    override fun convert(jwt: Jwt): AbstractAuthenticationToken {
        val supabaseIdString = jwt.subject
        val emailString = jwt.getClaimAsString("email")

        val principal = SupabaseAuthenticationPrincipal(supabaseIdString, emailString)
        val authorities = listOf(SimpleGrantedAuthority("ROLE_REGISTRATION"))

        return UsernamePasswordAuthenticationToken.authenticated(principal, null, authorities)
    }
}
