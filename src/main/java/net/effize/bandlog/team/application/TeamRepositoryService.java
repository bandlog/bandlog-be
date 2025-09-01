package net.effize.bandlog.team.application;

import net.effize.bandlog.team.domain.model.Member;
import net.effize.bandlog.team.domain.model.Team;
import net.effize.bandlog.team.domain.model.TeamId;
import net.effize.bandlog.team.domain.repository.MemberRepository;
import net.effize.bandlog.team.domain.repository.TeamRepository;
import net.effize.bandlog.team.domain.model.UserId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TeamRepositoryService {
    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;

    public TeamRepositoryService(TeamRepository teamRepository, MemberRepository memberRepository) {
        this.teamRepository = teamRepository;
        this.memberRepository = memberRepository;
    }

    public Team saveTeam(Team team) {
        return teamRepository.save(team);
    }

    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    public Team findTeamById(TeamId id) {
        return teamRepository.findById(id.value())
                .orElseThrow(() -> new IllegalArgumentException("Team not found with id: " + id.value()));
    }

    public List<Member> findMembersByTeam(Team team) {
        return memberRepository.findAllByTeam(team);
    }

    public List<Team> findTeamsByUserId(UserId userId) {
        return teamRepository.findAllByMembersUserId(userId.value());
    }
}