package net.effize.bandlog.domain.user.repository;

import net.effize.bandlog.domain.user.model.SupabaseUserId;
import net.effize.bandlog.domain.user.model.User;
import net.effize.bandlog.domain.user.model.UserId;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(UserId id);

    Optional<User> findBySupabaseId(SupabaseUserId id);

    User save(User user);
}
