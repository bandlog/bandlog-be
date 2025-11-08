package net.effize.bandlog.rehearsal.adapter.`in`.web.response

import java.time.Instant

data class RehearsalResponse(
    val id: Long,
    val teamId: Long,
    val title: String,
    val description: String?,
    val scheduledAt: Instant,
    val location: String?,
    val songs: List<Song>
) {
    data class Song(
        val id: Long,
        val title: String,
        val order: Int,
        val instrumentAssignments: List<InstrumentAssignment>
    ) {
        data class InstrumentAssignment(
            val instrument: String,
            val assignee: String?,
            val order: Int,
        )
    }
}