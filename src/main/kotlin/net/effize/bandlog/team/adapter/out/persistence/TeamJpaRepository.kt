package net.effize.bandlog.team.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TeamJpaRepository : JpaRepository<TeamJpaEntity, Long> {

    @Query("SELECT t FROM MemberJpaEntity m JOIN m.team t WHERE m.userId = :userId")
    fun findAllByMembersUserId(userId: Long): List<TeamJpaEntity>
}
