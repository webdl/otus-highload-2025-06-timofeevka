package ru.webdl.otus.socialnetwork;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Log4j2
public class MethodExecutionTimeLoggerAspect {

    @Around("execution(* ru.webdl.otus.socialnetwork.infra..*.*(..))")
//    @Around("execution(* ru.webdl.otus.socialnetwork.infra.security.jwt.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        try {
            Object result = joinPoint.proceed();
            return result;
        } finally {
            stopWatch.stop();
            long executionTime = stopWatch.getTotalTimeMillis();

            log.info("Method {}.{} executed in {} ms",
                    className, methodName, executionTime);
        }
    }
}
