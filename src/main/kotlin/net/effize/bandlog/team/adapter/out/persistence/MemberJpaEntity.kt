package net.effize.bandlog.team.adapter.out.persistence

import jakarta.persistence.*
import net.effize.bandlog.common.id.UserId
import net.effize.bandlog.team.domain.MemberId
import net.effize.bandlog.team.domain.MemberRole
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

@Entity
@Table(name = "members")
@EntityListeners(AuditingEntityListener::class)
class MemberJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    val team: TeamJpaEntity,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    val role: MemberRole,

    @Column(name = "created_at", nullable = false)
    val createdAt: Instant,

    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant
) {
    fun id(): MemberId = MemberId.of(id!!)
    fun userId(): UserId = UserId(userId)

    companion object {
        fun create(team: TeamJpaEntity, userId: UserId, role: MemberRole, now: Instant): MemberJpaEntity {
            return MemberJpaEntity(
                team = team,
                userId = userId.value,
                role = role,
                createdAt = now,
                updatedAt = now
            )
        }
    }
}
