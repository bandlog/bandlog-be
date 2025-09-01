package net.effize.bandlog.domain.team.repository;

import net.effize.bandlog.domain.team.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query("SELECT t FROM Member m JOIN Team t WHERE m.userId = :userId")
    List<Team> findAllByMembersUserId(Long userId);
}
