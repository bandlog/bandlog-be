package net.effize.bandlog.team.adapter;

import net.effize.bandlog.adapter.user.BandlogUserAdapter;
import net.effize.bandlog.team.model.User;
import net.effize.bandlog.team.model.UserId;
import net.effize.bandlog.team.port.UserPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserAdapter implements UserPort {
    private final BandlogUserAdapter bandlogUserAdapter;

    public UserAdapter(BandlogUserAdapter bandlogUserAdapter) {
        this.bandlogUserAdapter = bandlogUserAdapter;
    }

    @Override
    public List<User> findAllByIdIn(List<UserId> ids) {
        return bandlogUserAdapter.findAllByIdIn(ids.stream().map(UserId::value).toList())
                .stream().map(userResponse -> new User(
                        new UserId(userResponse.id()),
                        userResponse.email(),
                        userResponse.nickname()
                )).toList();
    }
}
