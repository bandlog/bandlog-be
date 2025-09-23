package net.effize.bandlog.rehearsal.model

import java.time.Instant

class RehearsalSongInstrument(
    val id: Long = 0L,
    val rehearsalSongId: Long,
    val instrument: String,
    val order: Int,
    val createdAt: Instant,
    val updatedAt: Instant
)