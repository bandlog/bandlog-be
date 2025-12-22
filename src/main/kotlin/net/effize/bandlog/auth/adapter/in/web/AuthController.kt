package net.effize.bandlog.auth.adapter.`in`.web

import net.effize.bandlog.auth.application.AuthService
import net.effize.bandlog.common.auth.AuthUser
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    fun signUp(authentication: Authentication) {
        authService.signup(authentication)
    }

    @GetMapping("/me")
    fun currentUser(authUser: AuthUser): String {
        return "hello ${authUser.nickname}"
    }
}
