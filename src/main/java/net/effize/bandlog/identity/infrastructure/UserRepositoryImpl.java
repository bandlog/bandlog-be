package net.effize.bandlog.identity.infrastructure;

import net.effize.bandlog.identity.domain.model.SupabaseUserId;
import net.effize.bandlog.identity.domain.model.User;
import net.effize.bandlog.identity.domain.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepositoryImpl extends UserRepository, JpaRepository<User, Long> {
}