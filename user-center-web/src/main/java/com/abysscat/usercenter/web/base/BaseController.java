package com.abysscat.usercenter.web.base;

import org.apache.commons.lang3.StringUtils;

import com.abysscat.usercenter.base.enums.ErrorEnum;
import com.abysscat.usercenter.base.web.BaseResponse;

/**
 * 基础Controller类
 */
public class BaseController {

    protected <T> BaseResponse<T> success(String msg, T res) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setError(ErrorEnum.SUCCESS);
        if (StringUtils.isNotBlank(msg)) {
            response.setMsg(msg);
        }
        response.setData(res);
        return response;
    }

    protected <T> BaseResponse<T> success() {
        return success(null, null);
    }

    protected <T> BaseResponse<T> success(T res) {
        return success(null, res);
    }

    protected BaseResponse error(ErrorEnum error, Throwable t) {
        BaseResponse vo = new BaseResponse();
        vo.setError(error);
        if (t != null) {
            vo.setThrowable(t);
        }
        return vo;
    }

    protected <T> BaseResponse<T> error(ErrorEnum error, String msg) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setError(error);
        if (StringUtils.isNotBlank(msg)) {
            response.setMsg(msg);
        }
        return response;
    }

    protected <T> BaseResponse<T> error(ErrorEnum error) {
        return error(error, "");
    }
}
