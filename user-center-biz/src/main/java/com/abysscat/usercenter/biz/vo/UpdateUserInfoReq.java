package com.abysscat.usercenter.biz.vo;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UpdateUserInfoReq {

    /**
     * 用户id
     */
    @NotNull(message = "用户ID不能为空")
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
