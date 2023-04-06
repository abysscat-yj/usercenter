package com.abysscat.usercenter.base.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.abysscat.usercenter.base.enums.ErrorEnum;
import com.abysscat.usercenter.base.web.BaseResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局异常处理器
 */
@Slf4j
@ControllerAdvice(basePackages = { "com.abysscat.usercenter" })
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public BaseResponse defaultHandler(HttpServletRequest request, Exception e) {

        if (isConnectionResetError(e)) {
            if (!StringUtils.isEmpty(e.getMessage())) {
                log.error(" exception_tag_{}, url: {}, message: {} ", "connection_reset", request.getRequestURI(),
                        e.getMessage());
            } else {
                log.error(" exception_tag_{}, url: {}", "connection_reset", request.getRequestURI());
            }
            return error(ErrorEnum.CONNECTION_RESET);
        }

        printErrorLog(request, "unknown", e);
        return error(ErrorEnum.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public BaseResponse paramHandler(HttpServletRequest request, HttpMessageNotReadableException e) {

        printErrorLog(request, "param", e);
        return error(ErrorEnum.PARAM_ERROR);
    }

    private BaseResponse error(BasicErrorCode errorCode) {
        BaseResponse vo = new BaseResponse();
        if (errorCode != null) {
            vo.setCode(errorCode.getCode());
            vo.setMsg(errorCode.getMsg());
        } else {
            vo.setError(ErrorEnum.INTERNAL_SERVER_ERROR);
        }
        return vo;
    }

    /**
     * 通过 Exception 判断，是属于对端断开的异常，不再返回500错误，减少接口在 SIA 上的 prvlost 统计
     * @return boolean
     */
    private boolean isConnectionResetError(Exception e) {
        if (e instanceof IOException) {
            String message = e.getMessage();
            if (!StringUtils.isEmpty(message)) {
                if (message.contains("Connection reset by peer")
                        || message.contains("Broken pipe") || message.contains("Remote peer closed")) {
                    return true;
                }
            }
        }
        return false;
    }

    private void printErrorLog(HttpServletRequest request, String tag, Exception e) {
        log.error(" exception_tag_{}, url: {} ", tag, request.getRequestURI(), e);
    }

}
