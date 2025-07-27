package net.effize.bandlog.api.auth;

import net.effize.bandlog.api.auth.resolver.AuthUserParam;
import net.effize.bandlog.application.auth.dto.AuthUser;
import net.effize.bandlog.domain.auth.model.AuthenticationPrincipal;
import net.effize.bandlog.domain.user.model.User;
import net.effize.bandlog.domain.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Random;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(Authentication authentication) {
        if (!(authentication.getPrincipal() instanceof AuthenticationPrincipal authenticationPrincipal)) {
            throw new IllegalStateException("Invalid authentication principal");
        }

        User newUser = User.create(
                authenticationPrincipal.getSupabaseUserId(),
                authenticationPrincipal.getEmail(),
                Instant.now(),
                new Random()
        );

        userRepository.save(newUser);
    }

    @GetMapping("/me")
    public String currentUser(@AuthUserParam AuthUser authUser) {
        return "hello " + authUser.nickname();
    }
}
