package com.suhoi.aspects;

import com.suhoi.model.Audit;
import com.suhoi.service.AuditService;
import com.suhoi.util.ApplicationContext;
import com.suhoi.util.UserUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.time.LocalDateTime;

/**
 * Аспект аудита. Предусмотрена ситуация аудита действий неавторизованного пользователя
 */
@Aspect
public class AuditAspect {

    private final AuditService auditService = (AuditService) ApplicationContext.getBeanMap().get("auditService");

    @Pointcut("@annotation(com.suhoi.annotation.Auditable) && execution(* *(..))")
    public void auditMethod() {
    }

    @Around("auditMethod()")
    public Object auditMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("audit aspect worked");
        String methodName = joinPoint.getSignature().getName();
        String username = null;
        Audit audit;
        Object proceed;
        try {
            username = UserUtils.getCurrentUser().getUsername();
            proceed = joinPoint.proceed();
        } catch (NullPointerException e) {
            audit = Audit.builder()
                    .username(null)
                    .action("Method " + methodName + " was called, but User not authenticated.")
                    .dateTime(LocalDateTime.now())
                    .build();
            auditService.save(audit);
            proceed = joinPoint.proceed();
            return proceed;
        }
        audit = Audit.builder()
                .username(username)
                .action("Method " + methodName + " executed")
                .dateTime(LocalDateTime.now())
                .build();
        auditService.save(audit);
        return proceed;
    }
}