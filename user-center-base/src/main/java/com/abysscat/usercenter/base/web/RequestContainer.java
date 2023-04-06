package com.abysscat.usercenter.base.web;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.aspectj.lang.JoinPoint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * 基础入参
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestContainer<T> {

    /**
     * 请求唯一标识id
     */
    private String reqId;

    /**
     * 调用方应用标识
     */
    private String appId;

    /**
     * 请求来源ip 标识请求方的
     * 如果需要依赖请求方之前的请求ip 写到接口入参的参数，请求方传递对应接口的入参，而非赋值该入参
     */
    private String clientIp;

    /**
     * 当前登录用户名
     */
    private String username;

    @Valid
    @NotNull(message = "请求数据不能为空！")
    private T data;

    public static RequestContainer getRequestContainer(JoinPoint joinPoint) {
        RequestContainer requestContainer = null;
        Object[] args = joinPoint.getArgs();
        if (null == args || args.length <= 0) {
            return requestContainer;
        }
        for (Object arg : args) {
            if (arg instanceof RequestContainer) {
                requestContainer = (RequestContainer) arg;
                break;
            }
        }
        if (null == requestContainer) {
            requestContainer = new RequestContainer();
            requestContainer.setData(args[0]);
        }
        return requestContainer;
    }

}
