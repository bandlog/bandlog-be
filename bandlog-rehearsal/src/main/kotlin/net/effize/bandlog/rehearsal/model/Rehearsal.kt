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

    @Column(name = "created_at")
    @CreatedDate
    val createdAt: Instant,

    @Column(name = "updated_at")
    @LastModifiedDate
    val updatedAt: Instant,
)