package com.abysscat.usercenter.biz.vo;

import lombok.Data;

@Data
public class GetAllUserInfoReq {

    /**
     * 页码
     */
    private Integer pageNum = 1;

    /**
     * 页面大小
     */
    private Integer pageSize = 10;

}
