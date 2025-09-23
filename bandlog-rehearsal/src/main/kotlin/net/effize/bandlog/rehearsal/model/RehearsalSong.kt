package net.effize.bandlog.rehearsal.model

import java.time.Instant

class RehearsalSong(
    val id: Long = 0L,
    val rehearsalId: Long,
    val title: String,
    val order: Int,
    val createdAt: Instant,
    val updatedAt: Instant
)