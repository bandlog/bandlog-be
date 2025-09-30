package net.effize.bandlog.rehearsal.model

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

@Entity
@Table(name = "rehearsal_song")
@EntityListeners(AuditingEntityListener::class)
class RehearsalSong(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(name = "title")
    val title: String,

    @Column(name = "order")
    val order: Int,

    @OneToMany(mappedBy = "rehearsalSong", cascade = [CascadeType.ALL], orphanRemoval = true)
    private val _instruments: MutableList<RehearsalSongInstrument> = mutableListOf(),
) {
    val instruments: List<RehearsalSongInstrument> = _instruments

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rehearsal_id")
    private lateinit var rehearsal: Rehearsal

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    lateinit var createdAt: Instant

    @Column(name = "updated_at")
    @LastModifiedDate
    lateinit var updatedAt: Instant

    constructor(title: String, order: Int) : this(
        id = 0L,
        title = title,
        order = order
    )

    fun assignRehearsal(rehearsal: Rehearsal) {
        this.rehearsal = rehearsal
    }

    fun lastInstrumentOrder(): Int {
        return instruments.maxOfOrNull { it.order } ?: 0
    }

    fun addInstrument(instrument: RehearsalSongInstrument) {
        _instruments.add(instrument)
        instrument.assignRehearsalSong(this)
    }
}