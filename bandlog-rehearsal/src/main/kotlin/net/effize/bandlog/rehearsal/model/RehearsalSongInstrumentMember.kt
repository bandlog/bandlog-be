package net.effize.bandlog.rehearsal.model

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant

@Entity
@Table(name = "rehearsal_song_instrument_member")
class RehearsalSongInstrumentMember(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rehearsal_song_instrument_id")
    val rehearsalSongInstrument: RehearsalSongInstrument,

    @Column(name = "member_id")
    val memberId: Long,

    @Column(name = "created_at")
    @CreatedDate
    val createdAt: Instant,

    @Column(name = "updated_at")
    @LastModifiedDate
    val updatedAt: Instant
)