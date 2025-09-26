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
    val description: String? = null,

    @Column(name = "scheduled_at")
    val scheduledAt: Instant,

    @Column(name = "location")
    val location: String? = null,

    @OneToMany(mappedBy = "rehearsal", cascade = [CascadeType.ALL], orphanRemoval = true)
    val songs: MutableList<RehearsalSong> = mutableListOf(),
) {
    @Column(name = "created_at", updatable = false)
    @CreatedDate
    lateinit var createdAt: Instant

    @Column(name = "updated_at")
    @LastModifiedDate
    lateinit var updatedAt: Instant
}