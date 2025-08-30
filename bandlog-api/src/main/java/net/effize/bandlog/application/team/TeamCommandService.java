package net.effize.bandlog.application.team;

import net.effize.bandlog.application.team.dto.*;
import net.effize.bandlog.domain.team.model.Member;
import net.effize.bandlog.domain.team.model.MemberRole;
import net.effize.bandlog.domain.team.model.Team;
import net.effize.bandlog.domain.team.model.TeamId;
import net.effize.bandlog.domain.team.service.TeamService;
import net.effize.bandlog.domain.user.model.UserId;

import java.time.Instant;
import java.util.List;

public class TeamCommandService {
    private final TeamService teamService;

    public TeamCommandService(TeamService teamService) {
        this.teamService = teamService;
    }

    public CreateTeamResponse createTeam(UserId authUserId, CreateTeamRequest request) {
        Instant now = Instant.now();
        Team createdTeam = teamService.createTeam(request.name(), request.description(), now);
        teamService.addNewMember(createdTeam, authUserId, MemberRole.LEADER, now);

        return new CreateTeamResponse(createdTeam.id().longValue());
    }

    public RefreshTeamInviteCodeResponse refreshTeamInviteCode(UserId authUserId, RefreshTeamInviteCodeRequest request) {
        Team foundTeam = teamService.activeTeam(TeamId.of(request.teamId()));
        foundTeam.refreshInviteCode();
        return new RefreshTeamInviteCodeResponse(foundTeam.id().longValue());
    }

    public JoinTeamResponse joinTeam(UserId authUserId, JoinTeamRequest request) {
        Instant now = Instant.now();
        Team foundTeam = teamService.activeTeam(TeamId.of(request.teamId()));
        List<Member> members = teamService.membersOf(foundTeam);
        long meCount = members.stream().filter((member) -> member.userId() == authUserId)
                .count();

        if (meCount > 0) throw new IllegalStateException("User already joined the team");

        teamService.addNewMember(foundTeam, authUserId, MemberRole.MEMBER, now);

        return new JoinTeamResponse(foundTeam.id().longValue());
    }
}
