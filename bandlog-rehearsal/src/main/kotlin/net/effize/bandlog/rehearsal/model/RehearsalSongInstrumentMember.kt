package net.effize.bandlog.rehearsal.model

import java.time.Instant

class RehearsalSongInstrumentMember(
    val id: Long = 0L,
    val rehearsalSongInstrumentId: Long,
    val memberId: Long,
    val createdAt: Instant,
    val updatedAt: Instant
)