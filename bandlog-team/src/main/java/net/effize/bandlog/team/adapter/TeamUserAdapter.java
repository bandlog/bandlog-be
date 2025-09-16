package net.effize.bandlog.team.adapter;

import net.effize.bandlog.adapter.user.UserAdapter;
import net.effize.bandlog.team.model.User;
import net.effize.bandlog.team.model.UserId;
import net.effize.bandlog.team.port.UserPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeamUserAdapter implements UserPort {
    private final UserAdapter userAdapter;

    public TeamUserAdapter(UserAdapter userAdapter) {
        this.userAdapter = userAdapter;
    }

    @Override
    public List<User> findAllByIdIn(List<UserId> ids) {
        return userAdapter.findAllByIdIn(ids.stream().map(UserId::value).toList())
                .stream().map(userResponse -> new User(
                        new UserId(userResponse.id()),
                        userResponse.email(),
                        userResponse.nickname()
                )).toList();
    }
}
