package com.abysscat.usercenter.web.aop.auth;

/**
 * usercenter鉴权类型枚举
 */
public enum AuthType {
    /**
     * uuap登录白名单
     */
    UUAP(1),

    /**
     * 外部服务端api调用鉴权
     */
    EXTERNAL_SERVER(2),

    /**
     * 外部客户端api调用鉴权
     */
    EXTERNAL_CLIENT(3),

    /**
     * 内部api调用鉴权
     */
    INTERNAL(4);

    private int value;

    AuthType(int value) {
        this.value = value;
    }
}
