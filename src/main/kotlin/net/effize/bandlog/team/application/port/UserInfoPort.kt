package net.effize.bandlog.team.application.port

import net.effize.bandlog.common.id.UserId

interface UserInfoPort {
    fun findById(userId: UserId): TeamUserInfo?
    fun findAllByIds(userIds: List<UserId>): List<TeamUserInfo>
}

data class TeamUserInfo(
    val id: UserId,
    val email: String,
    val nickname: String
)
