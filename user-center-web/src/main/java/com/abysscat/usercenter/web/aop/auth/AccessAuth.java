package com.abysscat.usercenter.web.aop.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * usercenter鉴权注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AccessAuth {

    /**
     * 默认需要uuap登录，然后进行白名单鉴权
     */
    AuthType type() default AuthType.UUAP;
}
