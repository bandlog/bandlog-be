package net.effize.bandlog.rehearsal.dto.request

data class ModifyRehearsalSongRequest(
    val teamId: Long,
    val title: String,
    val order: Int
)