package net.effize.bandlog.auth.adapter.out.user

import net.effize.bandlog.auth.application.port.AuthenticatedUserInfo
import net.effize.bandlog.auth.application.port.UserLookupPort
import net.effize.bandlog.user.api.UserQueryPort
import org.springframework.stereotype.Component

@Component
class UserLookupAdapter(
    private val userQueryPort: UserQueryPort
) : UserLookupPort {

    override fun findBySupabaseUserId(supabaseUserId: String): AuthenticatedUserInfo? {
        return userQueryPort.findBySupabaseUserId(supabaseUserId)?.let {
            AuthenticatedUserInfo(
                id = it.id,
                email = it.email,
                nickname = it.nickname
            )
        }
    }

    override fun signUp(supabaseUserId: String, email: String) {
        userQueryPort.signUp(supabaseUserId, email)
    }
}
