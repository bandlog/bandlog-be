package net.effize.bandlog.rehearsal.adapter.`in`.web.request

import java.time.Instant

data class ModifyRehearsalRequest(
    val teamId: Long,
    val title: String,
    val description: String?,
    val scheduledAt: Instant,
    val location: String?
)