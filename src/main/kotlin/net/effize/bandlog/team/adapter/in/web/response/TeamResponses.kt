package net.effize.bandlog.team.adapter.`in`.web.response

import net.effize.bandlog.team.domain.MemberRole

data class CreateTeamResponse(
    val teamId: Long
)

data class JoinTeamResponse(
    val teamId: Long
)

data class RefreshTeamInviteCodeResponse(
    val teamId: Long
)

data class TeamInfoResponse(
    val name: String,
    val description: String,
    val inviteCode: String,
    val members: List<MemberInfo>
) {
    data class MemberInfo(
        val nickname: String,
        val role: MemberRole
    )
}

data class TeamsResponse(
    val teams: List<TeamInfoResponse>
)
