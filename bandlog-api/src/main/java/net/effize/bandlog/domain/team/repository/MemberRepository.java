package net.effize.bandlog.domain.team.repository;

import net.effize.bandlog.domain.team.model.Member;
import net.effize.bandlog.domain.team.model.Team;

import java.util.List;

public interface MemberRepository {
    public Member save(Member member);

    public List<Member> findAllByTeam(Team team);
}
