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
    private var _title: String,

    @Column(name = "description")
    private var _description: String?,

    @Column(name = "scheduled_at")
    private var _scheduledAt: Instant,

    @Column(name = "location")
    private var _location: String?,

    @OneToMany(mappedBy = "rehearsal", cascade = [CascadeType.ALL], orphanRemoval = true)
    private val _songs: MutableList<RehearsalSong> = mutableListOf(),
) {
    val title: String
        get() = _title
    val description: String?
        get() = _description
    val scheduledAt: Instant
        get() = _scheduledAt
    val location: String?
        get() = _location
    val songs: List<RehearsalSong>
        get() = _songs

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    lateinit var createdAt: Instant

    @Column(name = "updated_at")
    @LastModifiedDate
    lateinit var updatedAt: Instant

    constructor(teamId: Long, title: String, description: String?, scheduledAt: Instant, location: String?) : this(
        id = 0L,
        teamId = teamId,
        _title = title,
        _description = description,
        _scheduledAt = scheduledAt,
        _location = location,
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

    fun modifyRehearsal(title: String, description: String?, scheduledAt: Instant, location: String?) {
        this._title = title
        this._description = description
        this._scheduledAt = scheduledAt
        this._location = location
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

    fun modifySongInstrument(songId: Long, songInstrumentId: Long, instrument: String) {
        val song = findSong(songId)
        song.modifyInstrument(songInstrumentId, instrument)
    }

    fun modifyInstrumentOrder(songId: Long, instrumentId: Long, newOrder: Int) {
        val song = findSong(songId)
        song.modifyInstrumentOrder(instrumentId, newOrder)
    }

    fun deleteSong(songId: Long) {
        val song = findSong(songId)
        // 마지막 위치로 이동한 후 삭제하여 order 재정렬
        reorderSongs(song, song.order, lastSongOrder())
        _songs.remove(song)
    }

    fun deleteSongInstrument(songId: Long, songInstrumentId: Long) {
        val song = findSong(songId)
        song.deleteInstrument(songInstrumentId)
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