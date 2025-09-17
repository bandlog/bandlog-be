package net.effize.bandlog.team.repository;

import net.effize.bandlog.team.model.Member;
import net.effize.bandlog.team.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    public List<Member> findAllByTeam(Team team);
}
