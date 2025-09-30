package net.effize.bandlog.port.team;

public interface BandlogTeamPort {
    boolean isUserLeaderOfTeam(long userId, long teamId);

    boolean isMemberOfTeam(long userId, long teamId);
}
