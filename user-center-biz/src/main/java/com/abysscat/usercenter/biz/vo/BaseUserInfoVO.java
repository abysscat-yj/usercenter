package com.abysscat.usercenter.biz.vo;

import lombok.Data;

/**
 * 用户基础信息VO
 */
@Data
public class BaseUserInfoVO {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户性别
     */
    private Integer gender;

    /**
     * 用户邮箱
     */
    private String email;

}
