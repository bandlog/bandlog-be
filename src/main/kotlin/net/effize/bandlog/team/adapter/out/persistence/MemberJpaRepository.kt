package net.effize.bandlog.team.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface MemberJpaRepository : JpaRepository<MemberJpaEntity, Long> {
    fun findAllByTeam(team: TeamJpaEntity): List<MemberJpaEntity>
}
