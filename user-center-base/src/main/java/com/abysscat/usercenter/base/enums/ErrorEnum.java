package com.abysscat.usercenter.base.enums;

import com.abysscat.usercenter.base.exception.BasicErrorCode;

import lombok.Getter;

/**
 * <p>
 * 0~999为预留系统通用错误码，业务错误码请从1000开始，不同模块可按1000间隔，便于分类排查问题
 * 特别说明：
 * 已有错误码请不要随便更改，下游会根据我们的错误码进行不同的业务处理
 *
 * <p>
 * 内部错误码 = 业务码(3) + 状态码(2) + 细分状态码(3)
 * 业务码  100    101      102         103
 * -----  通用  管理后台   搜索召回   流量配置
 * <p>
 * 状态码   00     01        02            03           04            05        06         07       99
 * -----  保留  参数错误   数据库异常    rpc调用异常    第三方服务异常     数据异常    业务错误     操作错误  其他异常
 *
 */
@Getter
public enum ErrorEnum implements BasicErrorCode {

    SUCCESS(0, "success"),
    PARAM_ERROR(601, "请求参数格式有误"),
    REGEX_FAILURE(602, "正则解析失败"),
    AUTH_REQUIRED(401, "登录信息不正确"),
    PERMISSION_DENIED(403, "没有该操作权限，请联系管理员"),
    NOT_FOUND(404, "未找到该资源!"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    DB_ERROR(501, "数据操作失败"),
    SERVER_BUSY(503, "服务器正忙，请稍后再试!"),
    CONNECTION_RESET(504, "Connection reset by peer"),
    OPERATION_FAILURE(505, "操作失败"),

    /**
     * api接口控制
     */
    API_AUTH_PARAMS_LACK(100_01_001, "api接口校验参数缺失"),
    API_SIGNATURE_INVALID(100_01_002, "api接口签名无效"),
    INNER_TOKEN_INVALID(100_01_003, "内部调用token无效"),
    REQUEST_TOO_FREQUENTLY(100_01_004, "请求过于频繁，请稍后再试"),

    /**
     * 数据库异常
     */
    INSERT_USER_ERROR(100_02_001, "插入用户记录失败"),

    /**
     * 第三方服务异常
     */

    /**
     * 数据异常相关
     */
    REGEX_ERROR(100_05_001, "正则格式错误"),

    /**
     * 业务异常
     */
    SEND_EMAIL_ERROR(100_06_001, "发送邮件错误"),
    USER_EXIST_ERROR(100_06_002, "用户已存在"),

    ;

    private final int code;

    private final String msg;

    ErrorEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
