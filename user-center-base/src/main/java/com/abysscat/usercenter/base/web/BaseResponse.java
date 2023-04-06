package com.abysscat.usercenter.base.web;

import java.util.Optional;

import com.abysscat.usercenter.base.enums.ErrorEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * 基础返参
 **/
@Data
@ToString
@AllArgsConstructor
public class BaseResponse<T> {

    private Integer code;

    private String msg;

    private T data;

    @JsonIgnore
    private Throwable throwable;

    public BaseResponse() {
        code = ErrorEnum.SUCCESS.getCode();
        msg = ErrorEnum.SUCCESS.getMsg();
    }

    public BaseResponse(ErrorEnum error) {
        error = Optional.ofNullable(error).orElse(ErrorEnum.SUCCESS);
        code = error.getCode();
        msg = error.getMsg();
    }

    public BaseResponse(ErrorEnum error, String msg) {
        error = Optional.ofNullable(error).orElse(ErrorEnum.SUCCESS);
        code = error.getCode();
        this.msg = msg;
    }

    public BaseResponse(String msg) {
        code = ErrorEnum.OPERATION_FAILURE.getCode();
        this.msg = msg;
    }

    public BaseResponse(ErrorEnum error, T data) {
        this(error);
        this.data = data;
    }

    public void setError(ErrorEnum error) {
        if (error != null) {
            this.code = error.getCode();
            this.msg = error.getMsg();
        }
    }

    public boolean hasSuccess() {
        return code == ErrorEnum.SUCCESS.getCode();
    }
}
