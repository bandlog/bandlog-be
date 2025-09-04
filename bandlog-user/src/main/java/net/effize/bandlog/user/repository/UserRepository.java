package net.effize.bandlog.user.repository;

import net.effize.bandlog.user.model.SupabaseUserId;
import net.effize.bandlog.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySupabaseUserId(SupabaseUserId id);

    Boolean existsBySupabaseUserId(SupabaseUserId id);

    List<User> findAllByIdIn(Collection<Long> ids);
}
