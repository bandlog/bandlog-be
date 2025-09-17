package net.effize.bandlog.team.adapter;

import net.effize.bandlog.port.user.BandlogUserPort;
import net.effize.bandlog.team.model.User;
import net.effize.bandlog.team.model.UserId;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserAdapter {
    private final BandlogUserPort bandlogUserPort;

    public UserAdapter(BandlogUserPort bandlogUserPort) {
        this.bandlogUserPort = bandlogUserPort;
    }

    public List<User> findAllByIdIn(List<UserId> ids) {
        return bandlogUserPort.findAllByIdIn(ids.stream().map(UserId::value).toList())
                .stream().map(userResponse -> new User(
                        new UserId(userResponse.id()),
                        userResponse.email(),
                        userResponse.nickname()
                )).toList();
    }
}
