package net.effize.bandlog.user.adapter.out.persistence

import net.effize.bandlog.user.domain.SupabaseUserId
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<UserJpaEntity, Long> {
    fun findBySupabaseUserId(supabaseUserId: SupabaseUserId): UserJpaEntity?
    fun existsBySupabaseUserId(supabaseUserId: SupabaseUserId): Boolean
    fun findAllByIdIn(ids: Collection<Long>): List<UserJpaEntity>
}
