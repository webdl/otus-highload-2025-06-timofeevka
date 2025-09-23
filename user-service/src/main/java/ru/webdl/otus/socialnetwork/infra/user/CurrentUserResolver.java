package ru.webdl.otus.socialnetwork.infra.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import ru.webdl.otus.socialnetwork.core.user.cases.UserFindUseCase;
import ru.webdl.otus.socialnetwork.core.user.entities.User;

@Configuration
public class CurrentUserResolver implements HandlerMethodArgumentResolver {
    private final UserFindUseCase userFindUseCase;

    @Autowired
    CurrentUserResolver(UserFindUseCase userFindUseCase) {
        this.userFindUseCase = userFindUseCase;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public User resolveArgument(MethodParameter parameter,
                                ModelAndViewContainer mavContainer,
                                NativeWebRequest webRequest,
                                WebDataBinderFactory binderFactory) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = (String) authentication.getPrincipal();
            return userFindUseCase.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        }

        throw new AccessDeniedException("User not authenticated");
    }
}
