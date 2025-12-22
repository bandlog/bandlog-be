package net.effize.bandlog.team.service;

import net.effize.bandlog.team.dto.request.CreateTeamRequest;
import net.effize.bandlog.team.dto.request.JoinTeamRequest;
import net.effize.bandlog.team.dto.request.RefreshTeamInviteCodeRequest;
import net.effize.bandlog.team.dto.response.CreateTeamResponse;
import net.effize.bandlog.team.dto.response.JoinTeamResponse;
import net.effize.bandlog.team.dto.response.RefreshTeamInviteCodeResponse;
import net.effize.bandlog.team.model.Member;
import net.effize.bandlog.team.model.MemberRole;
import net.effize.bandlog.team.model.Team;
import net.effize.bandlog.team.model.TeamId;
import net.effize.bandlog.team.model.UserId;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class TeamCommandService {
    private final TeamService teamService;

    public TeamCommandService(TeamService teamService) {
        this.teamService = teamService;
    }

    public CreateTeamResponse createTeam(UserId authUserId, CreateTeamRequest request) {
        Instant now = Instant.now();
        Team createdTeam = teamService.createTeam(request.name(), request.description(), now);
        teamService.addNewMember(createdTeam, authUserId, MemberRole.LEADER, now);

        return new CreateTeamResponse(createdTeam.id().value());
    }

    public RefreshTeamInviteCodeResponse refreshTeamInviteCode(UserId authUserId, RefreshTeamInviteCodeRequest request) {
        Team foundTeam = teamService.activeTeam(TeamId.of(request.teamId()));
        List<Member> members = teamService.membersOf(foundTeam);
        long meLeaderCount = members.stream()
                .filter((member) -> member.userId().equals(authUserId))
                .filter((member) -> member.role() == MemberRole.LEADER)
                .count();

        if (!(meLeaderCount > 0)) throw new IllegalStateException("User is not a leader of the team");

        foundTeam.refreshInviteCode();
        return new RefreshTeamInviteCodeResponse(foundTeam.id().value());
    }

    public JoinTeamResponse joinTeam(UserId authUserId, JoinTeamRequest request) {
        Instant now = Instant.now();
        Team foundTeam = teamService.activeTeam(TeamId.of(request.teamId()));
        List<Member> members = teamService.membersOf(foundTeam);
        long meCount = members.stream().filter((member) -> member.userId().equals(authUserId))
                .count();

        if (meCount > 0) throw new IllegalStateException("User already joined the team");

        teamService.addNewMember(foundTeam, authUserId, MemberRole.MEMBER, now);

        return new JoinTeamResponse(foundTeam.id().value());
    }
}
