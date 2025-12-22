package net.effize.bandlog.rehearsal.adapter.`in`.web.response

import java.time.Instant

data class RehearsalsResponse(
    val rehearsals: List<Rehearsal>
) {
    data class Rehearsal(
        val id: Long,
        val teamId: Long,
        val title: String,
        val scheduledAt: Instant,
        val location: String?,
    )
}
