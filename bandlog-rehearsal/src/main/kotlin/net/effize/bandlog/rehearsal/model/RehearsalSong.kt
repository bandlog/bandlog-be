package net.effize.bandlog.rehearsal.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
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
    private var title: String,

    @Column(name = "sort_order")
    private var _order: Int,

    @OneToMany(mappedBy = "rehearsalSong", cascade = [CascadeType.ALL], orphanRemoval = true)
    private val _instruments: MutableList<RehearsalSongInstrument> = mutableListOf(),
) {
    val instruments: List<RehearsalSongInstrument> = _instruments

    val order: Int
        get() = _order

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
        _order = order
    )

    fun assignRehearsal(rehearsal: Rehearsal) {
        this.rehearsal = rehearsal
    }

    fun addInstrumentWithName(instrumentName: String) {
        val order = lastInstrumentOrder() + 1
        val newInstrument = RehearsalSongInstrument(instrumentName, order)
        addInstrument(newInstrument)
    }

    fun modifyInstrument(instrumentId: Long, instrumentName: String, newOrder: Int) {
        validateInstrumentOrder(newOrder)

        val instrument = _instruments.find { it.id == instrumentId }
            ?: throw IllegalArgumentException("Rehearsal song instrument with id $instrumentId not found")

        val oldOrder = instrument.order

        instrument.modifyInstrument(instrumentName)

        if (oldOrder != newOrder) {
            reorderInstruments(instrument, oldOrder, newOrder)
        }
    }

    fun deleteInstrument(instrumentId: Long) {
        val instrument = _instruments.find { it.id == instrumentId }
            ?: throw IllegalArgumentException("Rehearsal song instrument with id $instrumentId not found")
        // 마지막 위치로 이동한 후 삭제하여 order 재정렬
        reorderInstruments(instrument, instrument.order, lastInstrumentOrder())
        _instruments.remove(instrument)
    }

    fun assignMemberToInstrument(instrumentId: Long, memberId: Long) {
        val instrument = _instruments.find { it.id == instrumentId }
            ?: throw IllegalArgumentException("Rehearsal song instrument with id $instrumentId not found")
        instrument.assignMember(memberId)
    }

    fun modifyTitle(title: String) {
        this.title = title
    }

    fun modifyOrder(order: Int) {
        this._order = order
    }

    fun modifyInstrumentOrder(instrumentId: Long, newOrder: Int) {
        validateInstrumentOrder(newOrder)

        val instrument = _instruments.find { it.id == instrumentId }
            ?: throw IllegalArgumentException("Rehearsal song instrument with id $instrumentId not found")

        val oldOrder = instrument.order

        if (oldOrder != newOrder) {
            reorderInstruments(instrument, oldOrder, newOrder)
        }
    }

    private fun lastInstrumentOrder(): Int {
        return instruments.maxOfOrNull { it.order } ?: 0
    }

    private fun addInstrument(instrument: RehearsalSongInstrument) {
        _instruments.add(instrument)
        instrument.assignRehearsalSong(this)
    }

    private fun validateInstrumentOrder(order: Int) {
        require(order in 1.._instruments.size) {
            "Order must be between 1 and ${_instruments.size}"
        }
    }

    /**
     * newOrder 값을 기준으로 기존 instrument들을 재정렬한다.
     * 기존 값들을 적절하게 Shift 하여 newOrder가 들어갈 공간을 만들어주는 방식
     */
    private fun reorderInstruments(targetInstrument: RehearsalSongInstrument, oldOrder: Int, newOrder: Int) {
        when {
            newOrder < oldOrder -> {
                _instruments.filter { it.order in newOrder..<oldOrder }
                    .forEach { it.modifyOrder(it.order + 1) }
            }

            newOrder > oldOrder -> {
                _instruments.filter { it.order in (oldOrder + 1)..newOrder }
                    .forEach { it.modifyOrder(it.order - 1) }
            }
        }
        targetInstrument.modifyOrder(newOrder)
    }
}