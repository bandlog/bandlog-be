package net.effize.bandlog.auth.application.port

import net.effize.bandlog.common.id.UserId

interface UserLookupPort {
    fun findBySupabaseUserId(supabaseUserId: String): AuthenticatedUserInfo?
    fun signUp(supabaseUserId: String, email: String)
}

data class AuthenticatedUserInfo(
    val id: UserId,
    val email: String,
    val nickname: String
)
