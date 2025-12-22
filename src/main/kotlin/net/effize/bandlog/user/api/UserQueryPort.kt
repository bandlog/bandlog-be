package net.effize.bandlog.user.api

import net.effize.bandlog.common.id.UserId

interface UserQueryPort {
    fun findById(userId: UserId): UserInfo?
    fun findAllByIds(userIds: List<UserId>): List<UserInfo>
    fun findBySupabaseUserId(supabaseUserId: String): UserInfo?
    fun signUp(supabaseUserId: String, email: String)
}
