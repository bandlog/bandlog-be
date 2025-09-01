package net.effize.bandlog.application.team;

import net.effize.bandlog.application.team.dto.TeamInfoResponse;
import net.effize.bandlog.application.team.dto.TeamsResponse;
import net.effize.bandlog.domain.team.model.Member;
import net.effize.bandlog.domain.team.model.Team;
import net.effize.bandlog.domain.team.model.TeamId;
import net.effize.bandlog.domain.team.service.TeamService;
import net.effize.bandlog.domain.user.model.User;
import net.effize.bandlog.domain.user.model.UserId;
import net.effize.bandlog.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TeamQueryService {
    private final TeamService teamService;
    private final UserRepository userRepository;

    public TeamQueryService(TeamService teamService, UserRepository userRepository) {
        this.teamService = teamService;
        this.userRepository = userRepository;
    }

    public TeamInfoResponse teamInfo(UserId authUserId, Long teamId) {
        Team foundTeam = teamService.activeTeam(TeamId.of(teamId));
        List<Member> members = teamService.membersOf(foundTeam);
        long meCount = members.stream().filter(member -> member.userId().equals(authUserId)).count();
        if (meCount <= 0) throw new IllegalStateException("User is not a member of the team");

        List<User> users = userRepository.findAllByIdIn(
                members
                        .stream()
                        .map(member -> member.userId().value())
                        .toList()
        );

        Map<Long, User> userMap = users.stream()
                .collect(Collectors.toMap(
                        user -> user.id().value(),
                        user -> user
                ));

        return new TeamInfoResponse(
                foundTeam.name(),
                foundTeam.description(),
                foundTeam.inviteCode(),
                members.stream()
                        .map(member -> new TeamInfoResponse.MemberInfo(
                                userMap.get(member.userId().value()).nickname().value(),
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

                    List<User> users = userRepository.findAllByIdIn(
                            members
                                    .stream()
                                    .map(member -> member.userId().value())
                                    .toList()
                    );

                    Map<Long, User> userMap = users.stream()
                            .collect(Collectors.toMap(
                                    user -> user.id().value(),
                                    user -> user
                            ));

                    return new TeamInfoResponse(
                            team.name(),
                            team.description(),
                            team.inviteCode(),
                            members.stream()
                                    .map(member -> new TeamInfoResponse.MemberInfo(
                                            userMap.get(member.userId().value()).nickname().value(),
                                            member.role()
                                    ))
                                    .toList()
                    );
                }).toList()
        );
    }
}
