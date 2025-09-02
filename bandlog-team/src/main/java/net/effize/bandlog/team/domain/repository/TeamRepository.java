package net.effize.bandlog.team.domain.repository;

import net.effize.bandlog.shared.kernel.types.UserId;
import net.effize.bandlog.team.domain.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    
    Optional<Team> findByInviteCode(String inviteCode);
    
    @Query("SELECT DISTINCT t FROM Team t JOIN t.members m WHERE m.userId = :userId")
    List<Team> findByMembersUserId(@Param("userId") UserId userId);
}