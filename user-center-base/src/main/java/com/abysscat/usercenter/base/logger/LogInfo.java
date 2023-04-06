package com.abysscat.usercenter.base.logger;

import lombok.Data;


@Data
public class LogInfo {

    private String reqId;

    private Long uid;

    private String methodName;

    private Integer status;

    /**
     * 时间 ms
     */
    private Long using;

    private String params;

    private String reqIp;

    private Integer errorCode;

    private String msg;

    private String traceId;

    private String requestURI;

    private String uuid;

    private String appVersion;

    private String platform;

    private String response;

    private String referrer;

    private String channelNum;

    private Integer bizType;

    private Boolean isLogin;

    private String clientIp;
}
