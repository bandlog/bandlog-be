package net.effize.bandlog.user.adapters.web.controllers;

import net.effize.bandlog.user.application.UserApplicationService;
import net.effize.bandlog.user.application.dto.AuthUser;
import net.effize.bandlog.user.adapters.web.dto.AuthUserParam;
import net.effize.bandlog.user.adapters.web.dto.UserProfileResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final UserApplicationService userApplicationService;

    public AuthController(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    @PostMapping("/auth/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(Authentication authentication) {
        userApplicationService.signup(authentication);
    }

    @GetMapping("/auth/me")
    public UserProfileResponse currentUser(@AuthUserParam AuthUser authUser) {
        return userApplicationService.getUserProfile(authUser.id());
    }
}