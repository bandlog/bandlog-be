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

    fun lastSongOrder(): Int {
        return songs.maxOfOrNull { it.order } ?: 0
    }

    fun addSong(rehearsalSong: RehearsalSong) {
        // order 중 가장 큰 값을 구하여 order가 겹치지 않게 합니다.
        _songs.add(rehearsalSong)
        rehearsalSong.assignRehearsal(this)
    }
}