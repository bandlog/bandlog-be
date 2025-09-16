package net.effize.bandlog.team.port;

import net.effize.bandlog.team.model.User;
import net.effize.bandlog.team.model.UserId;

import java.util.List;

public interface UserPort {
    List<User> findAllByIdIn(List<UserId> ids);
}
