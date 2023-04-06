package com.abysscat.usercenter.base.exception;

import java.util.function.Supplier;

import com.abysscat.usercenter.base.enums.ErrorEnum;

import lombok.Getter;
import lombok.Setter;

/**
 * 异常定义类
 **/
public class BusinessException extends RuntimeException implements Supplier<BusinessException> {

    @Getter
    @Setter
    private Integer errorCode;

    @Getter
    @Setter
    private String msg;

    public BusinessException(ErrorEnum errorCode) {
        super(String.format("BusinessException errorCode:%d, msg:%s",
                errorCode.getCode(), errorCode.getMsg()));
        this.errorCode = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }

    public BusinessException(String msg) {
        super(msg);
        this.errorCode = ErrorEnum.PARAM_ERROR.getCode();
        this.msg = msg;
    }

    public BusinessException(Integer errorCode, String msg) {
        this.errorCode = errorCode;
        this.msg = msg;
    }

    @Override
    public BusinessException get() {
        return this;
    }
}
