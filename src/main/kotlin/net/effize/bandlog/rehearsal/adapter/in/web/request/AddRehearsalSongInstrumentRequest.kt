package net.effize.bandlog.rehearsal.adapter.`in`.web.request

data class AddRehearsalSongInstrumentRequest(
    val teamId: Long,
    val instrument: String,
)
