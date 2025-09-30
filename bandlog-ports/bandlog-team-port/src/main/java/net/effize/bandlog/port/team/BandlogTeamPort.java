package net.effize.bandlog.port.team;

public interface BandlogTeamPort {
    boolean isUserLeaderOfTeam(long userId, long teamId);

    boolean isUserMemberOfTeam(long userId, long teamId);
}
