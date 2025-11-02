package net.effize.bandlog.rehearsal.adapter.`in`.web.request

data class ModifyRehearsalSongRequest(
    val teamId: Long,
    val title: String,
    val order: Int
)