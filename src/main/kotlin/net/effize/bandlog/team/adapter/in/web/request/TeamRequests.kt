package net.effize.bandlog.team.adapter.`in`.web.request

data class CreateTeamRequest(
    val name: String,
    val description: String
)

data class JoinTeamRequest(
    val teamId: Long
)

data class RefreshTeamInviteCodeRequest(
    val teamId: Long
)
