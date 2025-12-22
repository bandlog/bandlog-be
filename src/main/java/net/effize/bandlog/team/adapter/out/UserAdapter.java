package net.effize.bandlog.team.adapter.out;

import net.effize.bandlog.team.model.User;
import net.effize.bandlog.team.model.UserId;
import net.effize.bandlog.user.dto.response.UserResponse;
import net.effize.bandlog.user.service.UserService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserAdapter {
    private final UserService userService;

    public UserAdapter(UserService userService) {
        this.userService = userService;
    }

    public List<User> findAllByIdIn(List<UserId> ids) {
        return userService.findAllByIdIn(
                ids.stream()
                        .map(id -> new net.effize.bandlog.user.model.UserId(id.value()))
                        .toList()
        ).stream().map(userResponse -> new User(
                new UserId(userResponse.id()),
                userResponse.email(),
                userResponse.nickname()
        )).toList();
    }

    public User findById(UserId id) {
        UserResponse userResponse = userService.findById(new net.effize.bandlog.user.model.UserId(id.value()));
        return new User(
                new UserId(userResponse.id()),
                userResponse.email(),
                userResponse.nickname()
        );
    }
}
