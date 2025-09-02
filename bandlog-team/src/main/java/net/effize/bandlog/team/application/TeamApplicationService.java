package net.effize.bandlog.team.application;

import net.effize.bandlog.shared.kernel.types.TeamId;
import net.effize.bandlog.shared.kernel.types.UserId;
import net.effize.bandlog.team.domain.model.Member;
import net.effize.bandlog.team.domain.model.MemberRole;
import net.effize.bandlog.team.domain.model.Team;
import net.effize.bandlog.team.domain.repository.TeamRepository;
import net.effize.bandlog.team.adapters.web.dto.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class TeamApplicationService {
    
    private final TeamRepository teamRepository;
    private final ApplicationEventPublisher eventPublisher;
    
    public TeamApplicationService(TeamRepository teamRepository, ApplicationEventPublisher eventPublisher) {
        this.teamRepository = teamRepository;
        this.eventPublisher = eventPublisher;
    }
    
    public CreateTeamResponse createTeam(String name, String description, UserId creatorId) {
        Team team = Team.create(name, description, creatorId, Instant.now());
        Team saved = teamRepository.save(team);
        
        // Publish domain events
        saved.getEvents().forEach(eventPublisher::publishEvent);
        saved.clearEvents();
        
        return new CreateTeamResponse(
            saved.id().value(),
            saved.name(),
            saved.description(),
            saved.inviteCode()
        );
    }
    
    public RefreshTeamInviteCodeResponse refreshTeamInviteCode(UserId userId, Long teamId) {
        Team team = teamRepository.findById(teamId)
            .orElseThrow(() -> new IllegalArgumentException("Team not found"));
            
        // Check if user is owner or admin
        if (!team.isOwnerOrAdmin(userId)) {
            throw new IllegalArgumentException("Only team owners or admins can refresh invite code");
        }
        
        team.refreshInviteCode();
        Team saved = teamRepository.save(team);
        
        return new RefreshTeamInviteCodeResponse(
            saved.id().value(),
            saved.inviteCode()
        );
    }
    
    public JoinTeamResponse joinTeam(UserId userId, String inviteCode) {
        Team team = teamRepository.findByInviteCode(inviteCode)
            .orElseThrow(() -> new IllegalArgumentException("Invalid invite code"));
            
        team.addMember(userId, MemberRole.MEMBER, Instant.now());
        Team saved = teamRepository.save(team);
        
        // Publish domain events
        saved.getEvents().forEach(eventPublisher::publishEvent);
        saved.clearEvents();
        
        return new JoinTeamResponse(
            saved.id().value(),
            saved.name(),
            MemberRole.MEMBER.name()
        );
    }
    
    @Transactional(readOnly = true)
    public TeamInfoResponse getTeamInfo(UserId userId, Long teamId) {
        Team team = teamRepository.findById(teamId)
            .orElseThrow(() -> new IllegalArgumentException("Team not found"));
            
        // Check if user is a member
        if (!team.isMember(userId)) {
            throw new IllegalArgumentException("User is not a member of this team");
        }
        
        List<TeamInfoResponse.MemberInfo> memberInfos = team.members().stream()
            .map(member -> new TeamInfoResponse.MemberInfo(
                member.userId().value(),
                member.role().name(),
                member.joinedAt()
            ))
            .toList();
            
        return new TeamInfoResponse(
            team.id().value(),
            team.name(),
            team.description(),
            team.inviteCode(),
            team.createdAt(),
            memberInfos
        );
    }
    
    @Transactional(readOnly = true)
    public TeamsResponse getMyTeams(UserId userId) {
        List<Team> teams = teamRepository.findByMembersUserId(userId);
        
        List<TeamsResponse.TeamSummary> teamSummaries = teams.stream()
            .map(team -> {
                Member member = team.members().stream()
                    .filter(m -> m.userId().equals(userId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("User not found in team members"));
                    
                return new TeamsResponse.TeamSummary(
                    team.id().value(),
                    team.name(),
                    team.description(),
                    member.role().name(),
                    team.members().size(),
                    member.joinedAt()
                );
            })
            .toList();
            
        return new TeamsResponse(teamSummaries);
    }
}