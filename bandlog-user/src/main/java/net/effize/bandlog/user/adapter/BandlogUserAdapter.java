package net.effize.bandlog.user.adapter;

import net.effize.bandlog.port.user.BandlogAuthUserPort;
import net.effize.bandlog.port.user.BandlogUserPort;
import net.effize.bandlog.port.user.dto.BandlogUserResponse;
import net.effize.bandlog.user.model.Email;
import net.effize.bandlog.user.model.SupabaseUserId;
import net.effize.bandlog.user.model.UserId;
import net.effize.bandlog.user.service.UserService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BandlogUserAdapter implements BandlogUserPort, BandlogAuthUserPort {
    private final UserService userService;

    public BandlogUserAdapter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public List<BandlogUserResponse> findAllByIdIn(List<Long> ids) {
        return userService.findAllByIdIn(ids.stream().map(UserId::new).toList())
                .stream().map(userResponse -> new BandlogUserResponse(
                        userResponse.id(),
                        userResponse.supabaseUserId(),
                        userResponse.email(),
                        userResponse.nickname()
                )).toList();
    }

    @Override
    public void signUp(String supabaseUserId, String email) {
        userService.signUp(new SupabaseUserId(supabaseUserId), new Email(email));
    }

    @Override
    public Optional<BandlogUserResponse> findBySupabaseUserId(String supabaseUserId) {
        return userService.findBySupabaseUserId(new SupabaseUserId(supabaseUserId))
                .map(userResponse -> new BandlogUserResponse(
                        userResponse.id(),
                        userResponse.supabaseUserId(),
                        userResponse.email(),
                        userResponse.nickname()
                ));
    }
}
