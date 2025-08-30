package net.effize.bandlog.domain.team.repository;

import net.effize.bandlog.domain.team.model.Team;

import java.util.Optional;

public interface TeamRepository {
    public Team save(Team team);

    public Optional<Team> findById(Long id);
}
