package com.suhoi.aspects;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * Аспект логирования по аннотации. На прошлом интенсиве ментор сказал, что лучше делать так (в смысле по аннотации, а не по классам в пакете)
 */
@Aspect
@Component
@Slf4j
public class LoggableAspect {

    @Pointcut("@annotation(com.suhoi.annotation.Loggable) && execution(* *(..))")
    public void loggableMethod() {
    }

    @Around("loggableMethod()")
    public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        System.out.println("logg worked");
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.info("Calling method {}", methodSignature.toShortString());
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        stopWatch.stop();
        log.info("Execution of method {} finished. Execution time is {} ms", methodSignature.toShortString(), endTime - startTime);
        return result;
    }
}
