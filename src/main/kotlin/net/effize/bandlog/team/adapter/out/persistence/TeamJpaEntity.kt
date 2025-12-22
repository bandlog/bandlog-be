package net.effize.bandlog.team.adapter.out.persistence

import jakarta.persistence.*
import net.effize.bandlog.team.domain.TeamId
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "teams")
@EntityListeners(AuditingEntityListener::class)
class TeamJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null,

    @Column(name = "name")
    val name: String,

    @Column(name = "description")
    val description: String,

    @Column(name = "invite_code")
    var inviteCode: String,

    @Column(name = "created_at")
    val createdAt: Instant,

    @Column(name = "updated_at")
    var updatedAt: Instant
) {
    fun id(): TeamId = TeamId.of(id!!)

    fun refreshInviteCode() {
        this.inviteCode = generateInviteCode()
    }

    companion object {
        fun create(name: String, description: String, now: Instant): TeamJpaEntity {
            return TeamJpaEntity(
                name = name,
                description = description,
                inviteCode = generateInviteCode(),
                createdAt = now,
                updatedAt = now
            )
        }

        private fun generateInviteCode(): String = UUID.randomUUID().toString()
    }
}
