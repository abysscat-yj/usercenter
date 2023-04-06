package com.abysscat.usercenter.biz.vo;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class GetUserInfoReq {

    @NotNull(message = "用户ID列表不能为空")
    private List<Long> userIds;

}
