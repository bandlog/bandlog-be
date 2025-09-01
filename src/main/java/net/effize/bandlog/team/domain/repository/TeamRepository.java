package net.effize.bandlog.team.domain.repository;

import net.effize.bandlog.team.domain.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query("SELECT t FROM Member m JOIN Team t WHERE m.userId.value = :userId")
    List<Team> findAllByMembersUserId(Long userId);
}
