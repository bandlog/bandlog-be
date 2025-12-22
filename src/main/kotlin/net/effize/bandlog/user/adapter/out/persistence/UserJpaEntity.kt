package net.effize.bandlog.user.adapter.out.persistence

import jakarta.persistence.*
import net.effize.bandlog.common.id.UserId
import net.effize.bandlog.user.domain.Email
import net.effize.bandlog.user.domain.Nickname
import net.effize.bandlog.user.domain.SupabaseUserId
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.util.Random

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener::class)
class UserJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "supabase_user_id"))
    val supabaseUserId: SupabaseUserId,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "email"))
    val email: Email,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "nickname"))
    val nickname: Nickname,

    @CreatedDate
    @Column(name = "created_at")
    var createdAt: Instant? = null,

    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt: Instant? = null
) {
    fun id(): UserId = UserId(id!!)

    companion object {
        fun create(supabaseUserId: SupabaseUserId, email: Email, now: Instant, random: Random): UserJpaEntity {
            return UserJpaEntity(
                supabaseUserId = supabaseUserId,
                email = email,
                nickname = Nickname.randomNickname(random, now)
            )
        }
    }
}
