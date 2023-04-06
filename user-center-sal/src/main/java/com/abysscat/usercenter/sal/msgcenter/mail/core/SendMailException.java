package com.abysscat.usercenter.sal.msgcenter.mail.core;

/**
 * 发送邮件异常类
 */
public class SendMailException extends Exception {

    public SendMailException() {
    }

    public SendMailException(String message) {
        super(message);
    }

    public SendMailException(Throwable cause) {
        super(cause);
    }
}