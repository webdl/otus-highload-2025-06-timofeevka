package ru.webdl.otus.socialnetwork;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Log4j2
public class ExecutionTimeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        request.setAttribute("startTime", System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {
        Long startTime = (Long) request.getAttribute("startTime");
        long executionTime = System.currentTimeMillis() - startTime;

        log.info("{} {} executed in {} ms",
                request.getMethod(),
                request.getRequestURI(),
                executionTime);
    }
}
