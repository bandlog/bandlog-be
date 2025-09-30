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

    @Column(name = "sort_order")
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

    fun addInstrumentWithName(instrumentName: String) {
        val order = lastInstrumentOrder() + 1
        val newInstrument = RehearsalSongInstrument(instrumentName, order)
        addInstrument(newInstrument)
    }

    fun assignMemberToInstrument(instrumentId: Long, memberId: Long) {
        val instrument = _instruments.find { it.id == instrumentId }
            ?: throw IllegalArgumentException("Rehearsal song instrument with id $instrumentId not found")
        instrument.assignMember(memberId)
    }

    private fun lastInstrumentOrder(): Int {
        return instruments.maxOfOrNull { it.order } ?: 0
    }

    private fun addInstrument(instrument: RehearsalSongInstrument) {
        _instruments.add(instrument)
        instrument.assignRehearsalSong(this)
    }
}