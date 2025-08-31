package net.effize.bandlog.domain.team.service;

import net.effize.bandlog.domain.team.model.Member;
import net.effize.bandlog.domain.team.model.MemberRole;
import net.effize.bandlog.domain.team.model.Team;
import net.effize.bandlog.domain.team.model.TeamId;
import net.effize.bandlog.domain.team.repository.MemberRepository;
import net.effize.bandlog.domain.team.repository.TeamRepository;
import net.effize.bandlog.domain.user.model.UserId;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;

    public TeamService(TeamRepository teamRepository, MemberRepository memberRepository) {
        this.teamRepository = teamRepository;
        this.memberRepository = memberRepository;
    }

    public Team createTeam(String name, String description, Instant now) {
        Team newTeam = Team.create(name, description, now);
        return teamRepository.save(newTeam);
    }

    public Team addNewMember(Team team, UserId userId, MemberRole role, Instant now) {
        Member newMember = Member.create(team, userId, role, now);
        memberRepository.save(newMember);
        return team;
    }

    public Team activeTeam(TeamId id) {
        return teamRepository.findById(id.longValue())
                .orElseThrow();
    }

    public List<Member> membersOf(Team team) {
        return memberRepository.findAllByTeam(team);
    }

    public Team refreshInviteCode(TeamId teamId) {
        Team foundTeam = teamRepository.findById(teamId.longValue())
                .orElseThrow(() -> new IllegalArgumentException("Team not found"));
        foundTeam.refreshInviteCode();
        return teamRepository.save(foundTeam);
    }
}
