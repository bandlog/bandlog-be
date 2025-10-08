package net.effize.bandlog.rehearsal.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
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

    fun modifySong(songId: Long, title: String, newOrder: Int) {
        validateOrder(newOrder)

        val song = findSong(songId)
        val oldOrder = song.order

        song.modifyTitle(title)

        if (oldOrder != newOrder) {
            reorderSongs(song, oldOrder, newOrder)
        }
    }

    private fun validateOrder(order: Int) {
        require(order in 1.._songs.size) {
            "Order must be between 1 and ${_songs.size}"
        }
    }

    /**
     * newOrder 값을 기준으로 기존의 값들을 재정렬한다.
     * 기존 값들을 적절하게 Shift 하여 newOrder가 들어갈 공간을 만들어주는 방식
     */
    private fun reorderSongs(targetSong: RehearsalSong, oldOrder: Int, newOrder: Int) {
        when {
            newOrder < oldOrder -> {
                _songs.filter { it.order in newOrder..<oldOrder }
                    .forEach { it.modifyOrder(it.order + 1) }
            }

            newOrder > oldOrder -> {
                _songs.filter { it.order in (oldOrder + 1)..newOrder }
                    .forEach { it.modifyOrder(it.order - 1) }
            }
        }
        targetSong.modifyOrder(newOrder)
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