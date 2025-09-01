package net.effize.bandlog.auth.api;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public interface AuthUserResolver extends HandlerMethodArgumentResolver {
    
    @Override
    boolean supportsParameter(MethodParameter parameter);
    
    @Override
    Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                          NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception;
}