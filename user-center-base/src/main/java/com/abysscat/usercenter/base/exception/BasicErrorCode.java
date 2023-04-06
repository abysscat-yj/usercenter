package com.abysscat.usercenter.base.exception;

public interface BasicErrorCode {

    /**
     *  错误码.
     *
     * @return  错误码
     */
    int getCode();

    /**
     * 错误信息.
     *
     * @return 错误信息
     */
    String getMsg();

}
