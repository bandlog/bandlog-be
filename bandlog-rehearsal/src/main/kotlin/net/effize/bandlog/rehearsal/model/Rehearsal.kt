package net.effize.bandlog.rehearsal.model

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

@Entity
@Table(name = "rehearsal")
@EntityListeners(AuditingEntityListener::class)
class Rehearsal(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(name = "team_id")
    val teamId: Long,

    @Column(name = "title")
    val title: String,

    @Column(name = "description")
    val description: String?,

    @Column(name = "scheduled_at")
    val scheduledAt: Instant,

    @Column(name = "location")
    val location: String?,

    @OneToMany(mappedBy = "rehearsal", cascade = [CascadeType.ALL], orphanRemoval = true)
    private val _songs: MutableList<RehearsalSong> = mutableListOf(),
) {
    val songs: List<RehearsalSong> = _songs

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    lateinit var createdAt: Instant

    @Column(name = "updated_at")
    @LastModifiedDate
    lateinit var updatedAt: Instant

    constructor(teamId: Long, title: String, description: String?, scheduledAt: Instant, location: String?) : this(
        id = 0L,
        teamId = teamId,
        title = title,
        description = description,
        scheduledAt = scheduledAt,
        location = location,
    )

    fun addSongWithTitle(title: String) {
        val order = lastSongOrder() + 1
        val newSong = RehearsalSong(title, order)
        addSong(newSong)
    }

    fun addInstrumentToSong(songId: Long, instrument: String) {
        val song = findSong(songId)
        song.addInstrumentWithName(instrument)
    }

    fun assignMemberToInstrument(songId: Long, instrumentId: Long, memberId: Long) {
        val song = findSong(songId)
        song.assignMemberToInstrument(instrumentId, memberId)
    }

    private fun lastSongOrder(): Int {
        return songs.maxOfOrNull { it.order } ?: 0
    }

    private fun addSong(rehearsalSong: RehearsalSong) {
        _songs.add(rehearsalSong)
        rehearsalSong.assignRehearsal(this)
    }

    private fun findSong(songId: Long): RehearsalSong {
        return _songs.find { it.id == songId }
            ?: throw IllegalArgumentException("Rehearsal song with id $songId not found")
    }
}