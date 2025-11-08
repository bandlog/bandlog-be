package net.effize.bandlog.team.adapter.in;

import net.effize.bandlog.port.team.BandlogTeamPort;
import net.effize.bandlog.team.service.TeamQueryService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeamInAdapter implements BandlogTeamPort {
    private final TeamQueryService teamQueryService;

    public TeamInAdapter(TeamQueryService teamQueryService) {
        this.teamQueryService = teamQueryService;
    }

    @Override
    public List<Long> teamIdsOfUser(long userId) {
        return teamQueryService.teamsOfUser(userId);
    }

    @Override
    public String nicknameOfMember(long memberId) {
        return teamQueryService.nicknameOfMember(memberId);
    }

    @Override
    public boolean isUserLeaderOfTeam(long userId, long teamId) {
        return teamQueryService.isUserLeaderOfTeam(userId, teamId);
    }

    @Override
    public boolean isMemberOfTeam(long userId, long teamId) {
        return teamQueryService.isMemberOfTeam(userId, teamId);
    }
}
