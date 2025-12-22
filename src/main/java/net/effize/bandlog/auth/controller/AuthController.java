package net.effize.bandlog.auth.controller;

import net.effize.bandlog.auth.service.AuthService;
import net.effize.bandlog.shared.auth.AuthUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(Authentication authentication) {
        authService.signup(authentication);
    }

    @GetMapping("/me")
    public String currentUser(AuthUser authUser) {
        return "hello " + authUser.nickname();
    }
}
