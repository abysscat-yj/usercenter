package com.abysscat.usercenter.web.aop.auth;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * usercenter鉴权切面（根据业务需要定制逻辑）
 */
@Slf4j
@Order(2)
@Aspect
@Component
public class AccessAuthAspect {

    @Around("execution(public * com.abysscat.usercenter..*API.* (..)) && @annotation(auth)")
    public Object around(ProceedingJoinPoint pjp, AccessAuth auth) throws Throwable {

        // uuap白名单校验
        if (AuthType.UUAP == auth.type()) {


        // 外部服务端api鉴权
        } else if (AuthType.EXTERNAL_SERVER == auth.type()) {


        // 外部客户端api鉴权
        } else if (AuthType.EXTERNAL_CLIENT == auth.type()) {

        // 内部token鉴权
        } else if (AuthType.INTERNAL == auth.type()) {

        }
        return pjp.proceed();
    }

}
