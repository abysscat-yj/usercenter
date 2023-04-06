package com.abysscat.usercenter.sal.msgcenter.mail.vo;

import lombok.Data;

@Data
public class RegisterMailReq {

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户邮箱地址
     */
    private String userEmail;

}
