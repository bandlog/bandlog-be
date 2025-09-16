package net.effize.bandlog.adapter.user;

import net.effize.bandlog.user.dto.response.UserResponse;
import net.effize.bandlog.user.model.UserId;
import net.effize.bandlog.user.service.UserService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BandlogUserAdapter {
    private final UserService userService;

    public BandlogUserAdapter(UserService userService) {
        this.userService = userService;
    }

    public List<UserResponse> findAllByIdIn(List<Long> ids) {
        return userService.findAllByIdIn(ids.stream().map(UserId::new).toList());
    }
}
