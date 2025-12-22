package net.effize.bandlog.rehearsal.application.port

import net.effize.bandlog.common.id.UserId

interface TeamInfoPort {
    fun getTeamIdsOfUser(userId: UserId): List<Long>
    fun isUserLeaderOfTeam(userId: UserId, teamId: Long): Boolean
    fun isMemberOfTeam(memberId: Long, teamId: Long): Boolean
    fun getNicknameOfMember(memberId: Long): String
}
