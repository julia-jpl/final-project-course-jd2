package com.gmail.portnova.julia.service.aspect;

import lombok.extern.log4j.Log4j2;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.jdbc.core.SqlReturnType;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
@Log4j2
public class LoggingAspect {

    @Pointcut("execution(public * com.gmail.portnova.julia.service.*.*(..))")
    public void callAnyMethodAtService() {}

    @Around("callAnyMethodAtService()")
    public void logAroundMethod(JoinPoint joinPoint) {
        log.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        Object result = joinPoint.getTarget();
        log.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), result);
    }
}
