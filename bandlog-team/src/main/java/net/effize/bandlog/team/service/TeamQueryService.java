package net.effize.bandlog.team.service;

import net.effize.bandlog.team.dto.response.TeamInfoResponse;
import net.effize.bandlog.team.dto.response.TeamsResponse;
import net.effize.bandlog.team.model.Member;
import net.effize.bandlog.team.model.Team;
import net.effize.bandlog.team.model.TeamId;
import net.effize.bandlog.team.model.User;
import net.effize.bandlog.team.model.UserId;
import net.effize.bandlog.team.port.UserPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TeamQueryService {
    private final TeamService teamService;
    private final UserPort userPort;

    public TeamQueryService(TeamService teamService, UserPort userPort) {
        this.teamService = teamService;
        this.userPort = userPort;
    }

    public TeamInfoResponse teamInfo(UserId authUserId, Long teamId) {
        Team foundTeam = teamService.activeTeam(TeamId.of(teamId));
        List<Member> members = teamService.membersOf(foundTeam);
        long meCount = members.stream().filter(member -> member.userId().equals(authUserId)).count();
        if (meCount <= 0) throw new IllegalStateException("User is not a member of the team");

        List<User> users = userPort.findAllByIdIn(
                members
                        .stream()
                        .map(Member::userId)
                        .toList()
        );

        Map<Long, User> userMap = users.stream()
                .collect(Collectors.toMap(
                        user -> user.userId().value(),
                        user -> user
                ));

        return new TeamInfoResponse(
                foundTeam.name(),
                foundTeam.description(),
                foundTeam.inviteCode(),
                members.stream()
                        .map(member -> new TeamInfoResponse.MemberInfo(
                                userMap.get(member.userId().value()).nickname(),
                                member.role()
                        ))
                        .toList()
        );
    }

    public TeamsResponse myTeams(UserId authUserId) {
        List<Team> teams = teamService.teamsOfUser(authUserId);

        return new TeamsResponse(
                teams.stream().map(team -> {
                    List<Member> members = teamService.membersOf(team);

                    List<User> users = userPort.findAllByIdIn(
                            members
                                    .stream()
                                    .map(Member::userId)
                                    .toList()
                    );

                    Map<Long, User> userMap = users.stream()
                            .collect(Collectors.toMap(
                                    user -> user.userId().value(),
                                    user -> user
                            ));

                    return new TeamInfoResponse(
                            team.name(),
                            team.description(),
                            team.inviteCode(),
                            members.stream()
                                    .map(member -> new TeamInfoResponse.MemberInfo(
                                            userMap.get(member.userId().value()).nickname(),
                                            member.role()
                                    ))
                                    .toList()
                    );
                }).toList()
        );
    }
}
