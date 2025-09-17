package net.effize.bandlog.team.service;

import net.effize.bandlog.team.model.Member;
import net.effize.bandlog.team.model.MemberRole;
import net.effize.bandlog.team.model.Team;
import net.effize.bandlog.team.model.TeamId;
import net.effize.bandlog.team.model.UserId;
import net.effize.bandlog.team.repository.MemberRepository;
import net.effize.bandlog.team.repository.TeamRepository;
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
        return teamRepository.findById(id.value())
                .orElseThrow();
    }

    public List<Member> membersOf(Team team) {
        return memberRepository.findAllByTeam(team);
    }

    public List<Team> teamsOfUser(UserId userId) {
        return teamRepository.findAllByMembersUserId(userId.value());
    }
}
