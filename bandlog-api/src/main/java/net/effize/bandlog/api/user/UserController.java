package net.effize.bandlog.api.user;

import net.effize.bandlog.domain.user.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {


    @GetMapping("/me")
    public String getCurrentUser(Authentication authentication) {
        System.out.println(authentication);
        Object instance = authentication.getPrincipal();
        if (instance instanceof User) {
            return "hello " + authentication.getPrincipal().toString();
        }
        throw new RuntimeException("Invalid authentication");
    }
}
