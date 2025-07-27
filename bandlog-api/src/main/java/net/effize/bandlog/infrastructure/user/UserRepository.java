package net.effize.bandlog.infrastructure.user;

import net.effize.bandlog.domain.user.model.SupabaseUserId;
import net.effize.bandlog.domain.user.model.User;
import net.effize.bandlog.domain.user.model.UserId;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(UserId id);

    Optional<User> findBySupabaseUserId(SupabaseUserId id);

    User save(User user);
}
