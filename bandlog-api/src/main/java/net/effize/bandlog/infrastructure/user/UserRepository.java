package net.effize.bandlog.infrastructure.user;

import net.effize.bandlog.domain.user.model.SupabaseUserId;
import net.effize.bandlog.domain.user.model.User;
import net.effize.bandlog.domain.user.model.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, UserId> {
    Optional<User> findBySupabaseUserId(SupabaseUserId id);

    Boolean existsBySupabaseUserId(SupabaseUserId id);
}
