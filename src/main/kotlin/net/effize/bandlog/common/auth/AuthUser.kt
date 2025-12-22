package net.effize.bandlog.common.auth

data class AuthUser(
    val id: Long,
    val supabaseUserId: String,
    val email: String,
    val nickname: String
)
