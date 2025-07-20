package net.effize.bandlog.api.common.authuser;

import net.effize.bandlog.api.common.authuser.exception.IllegalAuthenticationException;
import net.effize.bandlog.api.common.authuser.exception.UserNotSignedUpException;
import net.effize.bandlog.domain.auth.model.AuthenticationPrincipal;
import net.effize.bandlog.domain.user.model.User;
import net.effize.bandlog.domain.user.repository.UserRepository;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class AuthUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserRepository userRepository;

    public AuthUserArgumentResolver(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthUser.class)
                && parameter.getParameterType().equals(User.class);

    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalAuthenticationException();
        }

        if (!(authentication.getPrincipal() instanceof AuthenticationPrincipal authPrincipal)) {
            throw new IllegalAuthenticationException();
        }

        return userRepository.findBySupabaseUserId(authPrincipal.getSupabaseUserId())
                .orElseThrow(() -> new UserNotSignedUpException());
    }
}
