package net.effize.bandlog.port.team;

import java.util.List;

public interface BandlogTeamPort {
    List<Long> teamIdsOfUser(long userId);

    String nicknameOfMember(long memberId);

    boolean isUserLeaderOfTeam(long userId, long teamId);

    boolean isMemberOfTeam(long userId, long teamId);
}
