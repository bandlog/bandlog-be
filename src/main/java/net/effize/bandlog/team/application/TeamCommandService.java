package net.effize.bandlog.team.application;

import net.effize.bandlog.team.application.dto.*;
import net.effize.bandlog.team.domain.model.Member;
import net.effize.bandlog.team.domain.model.MemberRole;
import net.effize.bandlog.team.domain.model.Team;
import net.effize.bandlog.team.domain.model.TeamId;
import net.effize.bandlog.team.domain.service.TeamDomainService;
import net.effize.bandlog.team.domain.model.UserId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class TeamCommandService {
    private final TeamDomainService teamDomainService;
    private final TeamRepositoryService teamRepositoryService;

    public TeamCommandService(TeamDomainService teamDomainService, TeamRepositoryService teamRepositoryService) {
        this.teamDomainService = teamDomainService;
        this.teamRepositoryService = teamRepositoryService;
    }

    public CreateTeamResponse createTeam(UserId authUserId, CreateTeamRequest request) {
        Instant now = Instant.now();
        
        // Domain Service로 비즈니스 로직 처리
        Team newTeam = teamDomainService.createTeam(request.name(), request.description(), now);
        Team savedTeam = teamRepositoryService.saveTeam(newTeam);
        
        // 팀 생성자를 리더로 추가
        Member leaderMember = teamDomainService.addMemberToTeam(savedTeam, authUserId, MemberRole.LEADER, now);
        teamRepositoryService.saveMember(leaderMember);

        return new CreateTeamResponse(savedTeam.id().value());
    }

    public RefreshTeamInviteCodeResponse refreshTeamInviteCode(UserId authUserId, RefreshTeamInviteCodeRequest request) {
        Team foundTeam = teamRepositoryService.findTeamById(TeamId.of(request.teamId()));
        List<Member> members = teamRepositoryService.findMembersByTeam(foundTeam);
        
        // Domain Service로 권한 검증
        teamDomainService.validateTeamLeaderPermission(members, authUserId);

        foundTeam.refreshInviteCode();
        teamRepositoryService.saveTeam(foundTeam);
        
        return new RefreshTeamInviteCodeResponse(foundTeam.id().value());
    }

    public JoinTeamResponse joinTeam(UserId authUserId, JoinTeamRequest request) {
        Instant now = Instant.now();
        Team foundTeam = teamRepositoryService.findTeamById(TeamId.of(request.teamId()));
        List<Member> members = teamRepositoryService.findMembersByTeam(foundTeam);
        
        // Domain Service로 비즈니스 규칙 검증
        teamDomainService.validateUserNotAlreadyMember(members, authUserId);

        Member newMember = teamDomainService.addMemberToTeam(foundTeam, authUserId, MemberRole.MEMBER, now);
        teamRepositoryService.saveMember(newMember);

        return new JoinTeamResponse(foundTeam.id().value());
    }
}
