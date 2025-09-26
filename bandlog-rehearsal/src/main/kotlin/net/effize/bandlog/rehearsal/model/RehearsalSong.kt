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
    val id: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rehearsal_id")
    val rehearsal: Rehearsal,

    @Column(name = "title")
    val title: String,

    @Column(name = "order")
    val order: Int,

    @OneToMany(mappedBy = "rehearsalSong", cascade = [CascadeType.ALL], orphanRemoval = true)
    val members: MutableList<RehearsalSongInstrumentMember> = mutableListOf(),
) {
    @Column(name = "created_at", updatable = false)
    @CreatedDate
    lateinit var createdAt: Instant

    @Column(name = "updated_at")
    @LastModifiedDate
    lateinit var updatedAt: Instant
}