package net.effize.bandlog.rehearsal.adapter.`in`.web.request

data class ModifyRehearsalSongInstrumentRequest(
    val teamId: Long,
    val instrument: String,
    val order: Int,
)