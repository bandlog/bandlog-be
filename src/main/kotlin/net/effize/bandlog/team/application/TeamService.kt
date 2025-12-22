package net.effize.bandlog.team.application

import net.effize.bandlog.common.id.UserId
import net.effize.bandlog.team.adapter.out.persistence.MemberJpaEntity
import net.effize.bandlog.team.adapter.out.persistence.MemberJpaRepository
import net.effize.bandlog.team.adapter.out.persistence.TeamJpaEntity
import net.effize.bandlog.team.adapter.out.persistence.TeamJpaRepository
import net.effize.bandlog.team.domain.MemberId
import net.effize.bandlog.team.domain.MemberRole
import net.effize.bandlog.team.domain.TeamId
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class TeamService(
    private val teamRepository: TeamJpaRepository,
    private val memberRepository: MemberJpaRepository
) {

    fun createTeam(name: String, description: String, now: Instant): TeamJpaEntity {
        val newTeam = TeamJpaEntity.create(name, description, now)
        return teamRepository.save(newTeam)
    }

    fun addNewMember(team: TeamJpaEntity, userId: UserId, role: MemberRole, now: Instant): TeamJpaEntity {
        val newMember = MemberJpaEntity.create(team, userId, role, now)
        memberRepository.save(newMember)
        return team
    }

    fun activeTeam(id: TeamId): TeamJpaEntity {
        return teamRepository.findById(id.value).orElseThrow()
    }

    fun membersOf(team: TeamJpaEntity): List<MemberJpaEntity> {
        return memberRepository.findAllByTeam(team)
    }

    fun teamsOfUser(userId: UserId): List<TeamJpaEntity> {
        return teamRepository.findAllByMembersUserId(userId.value)
    }

    fun memberOf(id: MemberId): MemberJpaEntity {
        return memberRepository.findById(id.value).orElseThrow()
    }
}
