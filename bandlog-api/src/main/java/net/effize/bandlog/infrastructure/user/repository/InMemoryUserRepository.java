package net.effize.bandlog.infrastructure.user.repository;

import net.effize.bandlog.domain.user.model.SupabaseUserId;
import net.effize.bandlog.domain.user.model.User;
import net.effize.bandlog.domain.user.model.UserId;
import net.effize.bandlog.domain.user.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class InMemoryUserRepository implements UserRepository {
    @Override
    public Optional<User> findById(UserId id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findBySupabaseId(SupabaseUserId id) {
        return Optional.empty();
    }

    @Override
    public User save(User user) {
        return null;
    }
}
