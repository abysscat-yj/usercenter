package com.abysscat.usercenter.base.enums;

import lombok.Getter;

@Getter
public enum IsDelEnum {
    NOT_DELETE(0, "未删除"),
    DELETED(1, "已删除");

    private final Integer status;

    private final String desc;

    IsDelEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
