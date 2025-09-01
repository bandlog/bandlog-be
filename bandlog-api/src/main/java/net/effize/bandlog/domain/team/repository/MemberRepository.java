package net.effize.bandlog.domain.team.repository;

import net.effize.bandlog.domain.team.model.Member;
import net.effize.bandlog.domain.team.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    public List<Member> findAllByTeam(Team team);
}
