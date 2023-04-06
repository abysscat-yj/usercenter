package com.abysscat.usercenter.base.exception;

import com.abysscat.usercenter.base.enums.ErrorEnum;

import lombok.Data;

/**
 * 自定义API异常，主要包括API层中前端传入的参数不合法异常
 */
@Data
public class ApiException extends RuntimeException{

    private ErrorEnum errorEnum;

    public ApiException(ErrorEnum errorEnum) {
        super(errorEnum.getMsg());
        this.errorEnum = errorEnum;
    }

    public ApiException(String msg) {
        super(msg);
    }

}
