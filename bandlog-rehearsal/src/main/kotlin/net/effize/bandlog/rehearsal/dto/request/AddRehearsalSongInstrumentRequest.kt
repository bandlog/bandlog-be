package net.effize.bandlog.rehearsal.dto.request

data class AddRehearsalSongInstrumentRequest(
    val teamId: Long,
    val instrument: String,
)
