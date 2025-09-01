package net.effize.bandlog.team.domain.service;

import net.effize.bandlog.team.domain.model.Member;
import net.effize.bandlog.team.domain.model.MemberRole;
import net.effize.bandlog.team.domain.model.Team;
import net.effize.bandlog.team.domain.model.UserId;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class TeamDomainService {

    public Team createTeam(String name, String description, Instant now) {
        validateTeamCreation(name, description);
        return Team.create(name, description, now);
    }

    public Member addMemberToTeam(Team team, UserId userId, MemberRole role, Instant now) {
        validateMemberAddition(team, userId, role);
        return Member.create(team, userId, role, now);
    }

    public void validateTeamLeaderPermission(List<Member> members, UserId userId) {
        boolean isLeader = members.stream()
                .filter(member -> member.userId().equals(userId))
                .anyMatch(member -> member.role() == MemberRole.LEADER);
        
        if (!isLeader) {
            throw new IllegalStateException("User is not a leader of the team");
        }
    }

    public void validateUserNotAlreadyMember(List<Member> members, UserId userId) {
        boolean isAlreadyMember = members.stream()
                .anyMatch(member -> member.userId().equals(userId));
        
        if (isAlreadyMember) {
            throw new IllegalStateException("User already joined the team");
        }
    }

    private void validateTeamCreation(String name, String description) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Team name cannot be empty");
        }
        if (name.length() > 100) {
            throw new IllegalArgumentException("Team name cannot exceed 100 characters");
        }
        if (description != null && description.length() > 500) {
            throw new IllegalArgumentException("Team description cannot exceed 500 characters");
        }
    }

    private void validateMemberAddition(Team team, UserId userId, MemberRole role) {
        if (team == null) {
            throw new IllegalArgumentException("Team cannot be null");
        }
        if (userId == null) {
            throw new IllegalArgumentException("UserId cannot be null");
        }
        if (role == null) {
            throw new IllegalArgumentException("Member role cannot be null");
        }
    }
}