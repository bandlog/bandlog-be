package net.effize.bandlog.rehearsal.model

import java.time.Instant


class Rehearsal(
    val id: Long = 0L,
    val teamId: Long,
    val title: String,
    val description: String? = null,
    val scheduledAt: Instant,
    val location: String? = null,
    val createdAt: Instant,
    val updatedAt: Instant,
)