package net.effize.bandlog.auth.infrastructure.resolver;

import net.effize.bandlog.auth.api.AuthUserResolver;
import net.effize.bandlog.auth.application.AuthApplicationService;
import net.effize.bandlog.auth.api.AuthUser;
import net.effize.bandlog.auth.api.AuthUserParam;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class AuthUserArgumentResolver implements AuthUserResolver {

    private final AuthApplicationService authApplicationService;

    public AuthUserArgumentResolver(AuthApplicationService authApplicationService) {
        this.authApplicationService = authApplicationService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthUserParam.class)
                && parameter.getParameterType().equals(AuthUser.class);

    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authApplicationService.authenticate(authentication);
    }
}