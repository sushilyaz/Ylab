package com.suhoi.aspects;

import com.suhoi.exception.UserActionException;
import com.suhoi.model.User;
import com.suhoi.util.UserUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Т.к. спринг секьюрити нельзя, сделал через аспект
 */
@Aspect
@Component
public class AuthorizeAspect {

    @Pointcut("@annotation(com.suhoi.annotation.Authorize) && execution(* *(..))")
    public void authorizeMethod() {
    }

    @Before("authorizeMethod()")
    public void authorize() {
        User currentUser = UserUtils.getCurrentUser();
        if (currentUser == null) {
            throw new UserActionException("User not authorized");
        }
    }
}
