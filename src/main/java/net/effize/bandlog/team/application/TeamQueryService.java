package net.effize.bandlog.team.application;

import net.effize.bandlog.team.application.dto.TeamInfoResponse;
import net.effize.bandlog.team.application.dto.TeamsResponse;
import net.effize.bandlog.team.application.converter.MemberRoleConverter;
import net.effize.bandlog.team.domain.model.Member;
import net.effize.bandlog.team.domain.model.Team;
import net.effize.bandlog.team.domain.model.TeamId;
import net.effize.bandlog.identity.api.UserService;
import net.effize.bandlog.identity.api.dto.UserDto;
import net.effize.bandlog.team.domain.model.UserId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TeamQueryService {
    private final TeamRepositoryService teamRepositoryService;
    private final UserService userService;
    private final MemberRoleConverter memberRoleConverter;

    public TeamQueryService(TeamRepositoryService teamRepositoryService, UserService userService, MemberRoleConverter memberRoleConverter) {
        this.teamRepositoryService = teamRepositoryService;
        this.userService = userService;
        this.memberRoleConverter = memberRoleConverter;
    }

    public TeamInfoResponse teamInfo(UserId authUserId, Long teamId) {
        Team foundTeam = teamRepositoryService.findTeamById(TeamId.of(teamId));
        List<Member> members = teamRepositoryService.findMembersByTeam(foundTeam);
        long meCount = members.stream().filter(member -> member.userId().equals(authUserId)).count();
        if (meCount <= 0) throw new IllegalStateException("User is not a member of the team");

        List<UserDto> users = userService.findAllByIdIn(
                members
                        .stream()
                        .map(member -> member.userId().value())
                        .toList()
        );

        Map<Long, UserDto> userMap = users.stream()
                .collect(Collectors.toMap(
                        user -> user.id(),
                        user -> user
                ));

        return new TeamInfoResponse(
                foundTeam.name(),
                foundTeam.description(),
                foundTeam.inviteCode(),
                members.stream()
                        .map(member -> new TeamInfoResponse.MemberInfo(
                                userMap.get(member.userId().value()).nickname(),
                                memberRoleConverter.toDto(member.role())
                        ))
                        .toList()
        );
    }

    public TeamsResponse myTeams(UserId authUserId) {
        List<Team> teams = teamRepositoryService.findTeamsByUserId(authUserId);

        return new TeamsResponse(
                teams.stream().map(team -> {
                    List<Member> members = teamRepositoryService.findMembersByTeam(team);

                    List<UserDto> users = userService.findAllByIdIn(
                            members
                                    .stream()
                                    .map(member -> member.userId().value())
                                    .toList()
                    );

                    Map<Long, UserDto> userMap = users.stream()
                            .collect(Collectors.toMap(
                                    user -> user.id(),
                                    user -> user
                            ));

                    return new TeamInfoResponse(
                            team.name(),
                            team.description(),
                            team.inviteCode(),
                            members.stream()
                                    .map(member -> new TeamInfoResponse.MemberInfo(
                                            userMap.get(member.userId().value()).nickname(),
                                            memberRoleConverter.toDto(member.role())
                                    ))
                                    .toList()
                    );
                }).toList()
        );
    }
}
