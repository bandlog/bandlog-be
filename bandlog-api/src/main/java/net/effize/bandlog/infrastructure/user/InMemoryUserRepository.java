package net.effize.bandlog.infrastructure.user;

import net.effize.bandlog.domain.user.model.Email;
import net.effize.bandlog.domain.user.model.SupabaseUserId;
import net.effize.bandlog.domain.user.model.User;
import net.effize.bandlog.domain.user.model.UserId;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;
import java.util.Random;

@Component
public class InMemoryUserRepository implements UserRepository {
    @Override
    public Optional<User> findById(UserId id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findBySupabaseUserId(SupabaseUserId id) {
        return Optional.of(
                User.create(SupabaseUserId.of("aser"), Email.of("asdb@example.com"), Instant.now(), new Random())
        );
    }

    @Override
    public User save(User user) {
        return user;
    }
}
