package net.effize.bandlog.auth.application

import net.effize.bandlog.auth.application.port.UserLookupPort
import net.effize.bandlog.auth.domain.SupabaseAuthenticationPrincipal
import net.effize.bandlog.auth.domain.exception.IllegalAuthenticationException
import net.effize.bandlog.auth.domain.exception.UserNotSignedUpException
import net.effize.bandlog.common.auth.AuthUser
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val userLookupPort: UserLookupPort
) {

    @Transactional
    fun signup(authentication: Authentication) {
        val principal = authentication.principal
        if (principal !is SupabaseAuthenticationPrincipal) {
            throw IllegalAuthenticationException()
        }

        userLookupPort.signUp(principal.supabaseUserId, principal.email)
    }

    @Transactional(readOnly = true)
    fun authenticate(authentication: Authentication?): AuthUser {
        if (authentication == null || !authentication.isAuthenticated) {
            throw IllegalAuthenticationException()
        }

        val principal = authentication.principal
        if (principal !is SupabaseAuthenticationPrincipal) {
            throw IllegalAuthenticationException()
        }

        val userInfo = userLookupPort.findBySupabaseUserId(principal.supabaseUserId)
            ?: throw UserNotSignedUpException()

        return AuthUser(
            id = userInfo.id.value,
            supabaseUserId = principal.supabaseUserId,
            email = userInfo.email,
            nickname = userInfo.nickname
        )
    }
}
