package com.abysscat.usercenter.biz.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class RegisterUserReq {

    /**
     * 用户头像
     */
    @NotBlank(message = "用户头像不能为空")
    private String avatar;

    /**
     * 用户昵称
     */
    @NotBlank(message = "用户昵称不能为空")
    private String nickname;

    /**
     * 用户性别
     */
    @NotNull(message = "用户性别不能为空")
    private Integer gender;

    /**
     * 用户邮箱
     */
    @NotBlank(message = "用户邮箱不能为空")
    @Pattern(regexp = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$", message = "邮箱格式不正确")
    private String email;

}
