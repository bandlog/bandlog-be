package net.effize.bandlog.application.team;

import net.effize.bandlog.application.team.dto.CreateTeamRequest;
import net.effize.bandlog.application.team.dto.CreateTeamResponse;
import net.effize.bandlog.application.team.dto.RefreshTeamInviteCodeRequest;
import net.effize.bandlog.application.team.dto.RefreshTeamInviteCodeResponse;
import net.effize.bandlog.domain.team.model.MemberRole;
import net.effize.bandlog.domain.team.model.Team;
import net.effize.bandlog.domain.team.model.TeamId;
import net.effize.bandlog.domain.team.service.TeamService;
import net.effize.bandlog.domain.user.model.UserId;

import java.time.Instant;

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
}
