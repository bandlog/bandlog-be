package net.effize.bandlog.team.api

import net.effize.bandlog.common.id.UserId

interface TeamQueryPort {
    fun getTeamIdsOfUser(userId: UserId): List<Long>
    fun isUserLeaderOfTeam(userId: UserId, teamId: Long): Boolean
    fun isMemberOfTeam(userId: UserId, teamId: Long): Boolean
    fun getNicknameOfMember(memberId: Long): String
}
