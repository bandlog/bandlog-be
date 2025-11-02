package net.effize.bandlog.rehearsal.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

@Entity
@Table(name = "rehearsal_song_instruments")
@EntityListeners(AuditingEntityListener::class)
class RehearsalSongInstrument(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(name = "instrument")
    private var _instrument: String,

    @Column(name = "sort_order")
    private var _order: Int,

    @Column(name = "member_id")
    private var _memberId: Long? = null,
) {
    val instrument: String
        get() = _instrument

    val order: Int
        get() = _order

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rehearsal_song_id")
    private var rehearsalSong: RehearsalSong? = null


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

    fun detachFromRehearsalSong() {
        this.rehearsalSong = null
    }

    fun assignMember(memberId: Long) {
        this._memberId = memberId
    }

    fun modifyInstrument(newInstrument: String) {
        this._instrument = newInstrument
    }

    fun modifyOrder(order: Int) {
        this._order = order
    }
}