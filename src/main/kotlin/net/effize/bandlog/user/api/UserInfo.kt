package net.effize.bandlog.user.api

import net.effize.bandlog.common.id.UserId

data class UserInfo(
    val id: UserId,
    val supabaseUserId: String,
    val email: String,
    val nickname: String
)
