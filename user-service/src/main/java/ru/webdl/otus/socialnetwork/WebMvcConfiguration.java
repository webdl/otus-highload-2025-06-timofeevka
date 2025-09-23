package ru.webdl.otus.socialnetwork;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.webdl.otus.socialnetwork.infra.user.CurrentUserResolver;

import java.util.List;

@Configuration
class WebMvcConfiguration implements WebMvcConfigurer {
    private final ExecutionTimeInterceptor executionTimeInterceptor;
    private final CurrentUserResolver currentUserResolver;

    @Autowired
    WebMvcConfiguration(ExecutionTimeInterceptor executionTimeInterceptor,
                        CurrentUserResolver currentUserResolver) {
        this.executionTimeInterceptor = executionTimeInterceptor;
        this.currentUserResolver = currentUserResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(executionTimeInterceptor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(currentUserResolver);
    }
}
