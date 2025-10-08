package net.effize.bandlog.rehearsal.dto.request

data class ModifyRehearsalSongInstrumentRequest(
    val teamId: Long,
    val instrument: String,
    val order: Int,
)