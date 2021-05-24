package com.gmail.portnova.julia.service.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Log4j2
public class LoggingAspect {

    @Pointcut("execution(public * com.gmail.portnova.julia.service.impl.*.*(..))")
    public void callAnyMethodAtService() {
    }

    @Before("callAnyMethodAtService()")
    public void logAroundMethod(JoinPoint joinPoint) {
        log.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
    }
    @After("callAnyMethodAtService()")
    public void callAfterMethod(JoinPoint joinPoint) {
        Object result = joinPoint.getTarget();
        log.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), result);
    }
}
