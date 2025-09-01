package net.effize.bandlog.auth.controller;

import net.effize.bandlog.auth.api.AuthUser;
import net.effize.bandlog.auth.api.AuthUserParam;
import net.effize.bandlog.auth.application.AuthApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthApplicationService authApplicationService;

    public AuthController(AuthApplicationService authApplicationService) {
        this.authApplicationService = authApplicationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(Authentication authentication) {
        authApplicationService.signup(authentication);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<AuthUser> me(@AuthUserParam AuthUser authUser) {
        return ResponseEntity.ok(authUser);
    }
}