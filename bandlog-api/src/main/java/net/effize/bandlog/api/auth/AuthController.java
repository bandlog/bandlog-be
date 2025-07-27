package net.effize.bandlog.api.auth;

import net.effize.bandlog.api.auth.resolver.AuthUserParam;
import net.effize.bandlog.application.auth.AuthApplicationService;
import net.effize.bandlog.application.auth.dto.AuthUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthApplicationService authApplicationService;

    public AuthController(AuthApplicationService authApplicationService) {
        this.authApplicationService = authApplicationService;
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(Authentication authentication) {
        authApplicationService.signup(authentication);
    }

    @GetMapping("/me")
    public String currentUser(@AuthUserParam AuthUser authUser) {
        return "hello " + authUser.nickname();
    }
}
