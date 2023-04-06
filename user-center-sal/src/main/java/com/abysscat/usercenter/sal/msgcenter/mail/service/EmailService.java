package com.abysscat.usercenter.sal.msgcenter.mail.service;

import com.abysscat.usercenter.sal.msgcenter.mail.vo.RegisterMailReq;

public interface EmailService {

    /**
     * 发送用户注册成功邮件
     * @param mailReq
     */
    void SendRegisterMail(RegisterMailReq mailReq) throws Exception;

}
