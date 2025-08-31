package net.effize.bandlog.domain.team.repository;

import net.effize.bandlog.domain.team.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
