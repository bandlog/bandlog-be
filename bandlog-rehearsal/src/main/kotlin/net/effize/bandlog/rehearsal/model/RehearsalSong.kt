package net.effize.bandlog.rehearsal.model

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant

@Entity
@Table(name = "rehearsal_song")
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

    @Column(name = "created_at")
    @CreatedDate
    val createdAt: Instant,

    @OneToMany(mappedBy = "rehearsalSong", cascade = [CascadeType.ALL], orphanRemoval = true)
    val members: MutableList<RehearsalSongInstrumentMember> = mutableListOf(),

    @Column(name = "updated_at")
    @LastModifiedDate
    val updatedAt: Instant
)