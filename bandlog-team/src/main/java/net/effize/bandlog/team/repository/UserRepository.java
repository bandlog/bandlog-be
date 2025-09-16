package net.effize.bandlog.team.repository;

import net.effize.bandlog.team.model.User;
import net.effize.bandlog.team.model.UserId;

import java.util.List;

public interface UserRepository {
    List<User> findAllByIdIn(List<UserId> ids);
}
