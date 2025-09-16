package net.effize.bandlog.adapter.user;

import net.effize.bandlog.adapter.user.dto.BandlogUserResponse;
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

    public List<BandlogUserResponse> findAllByIdIn(List<Long> ids) {
        return userService.findAllByIdIn(ids.stream().map(UserId::new).toList())
                .stream().map(userResponse -> new BandlogUserResponse(
                        userResponse.id(),
                        userResponse.supabaseUserId(),
                        userResponse.email(),
                        userResponse.nickname()
                )).toList();
    }
}
