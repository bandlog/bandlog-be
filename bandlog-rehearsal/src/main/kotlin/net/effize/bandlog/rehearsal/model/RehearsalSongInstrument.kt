package net.effize.bandlog.rehearsal.model

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

@Entity
@Table(name = "rehearsal_song_instrument")
@EntityListeners(AuditingEntityListener::class)
class RehearsalSongInstrument(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(name = "instrument")
    val instrument: String,

    @Column(name = "sort_order")
    val order: Int,

    @Column(name = "member_id")
    private var _memberId: Long? = null,
) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rehearsal_song_id")
    private lateinit var rehearsalSong: RehearsalSong


    @Column(name = "created_at", updatable = false)
    @CreatedDate
    lateinit var createdAt: Instant

    @Column(name = "updated_at")
    @LastModifiedDate
    lateinit var updatedAt: Instant

    constructor(instrument: String, order: Int) : this(0L, instrument, order)

    fun assignRehearsalSong(rehearsalSong: RehearsalSong) {
        this.rehearsalSong = rehearsalSong
    }

    fun assignMember(memberId: Long) {
        this._memberId = memberId
    }
}