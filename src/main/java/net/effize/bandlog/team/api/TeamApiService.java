package net.effize.bandlog.team.api;

import net.effize.bandlog.team.api.dto.MemberDto;
import net.effize.bandlog.team.api.dto.TeamDto;
import net.effize.bandlog.team.api.dto.TeamIdDto;

import java.util.List;

public interface TeamApiService {
    
    List<TeamDto> getTeamsByUserId(Long userId);
    
    TeamDto getTeamById(TeamIdDto teamId);
    
    List<MemberDto> getMembersByTeam(TeamIdDto teamId);
    
    TeamIdDto createTeam(String name, String description, Long creatorUserId);
    
    void joinTeam(TeamIdDto teamId, Long userId);
    
    void refreshTeamInviteCode(TeamIdDto teamId, Long requestUserId);
}