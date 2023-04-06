package com.abysscat.usercenter.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abysscat.usercenter.base.web.BaseResponse;
import com.abysscat.usercenter.base.web.RequestContainer;
import com.abysscat.usercenter.biz.service.UserService;
import com.abysscat.usercenter.biz.vo.BanUserReq;
import com.abysscat.usercenter.biz.vo.GetAllUserInfoReq;
import com.abysscat.usercenter.biz.vo.GetUserInfoReq;
import com.abysscat.usercenter.biz.vo.GetUserInfoResp;
import com.abysscat.usercenter.biz.vo.RegisterUserReq;
import com.abysscat.usercenter.biz.vo.UpdateUserInfoReq;
import com.abysscat.usercenter.web.aop.auth.AccessAuth;
import com.abysscat.usercenter.web.base.BaseController;
import com.abysscat.usercenter.web.config.SentinelConfig;
import com.alibaba.csp.sentinel.annotation.SentinelResource;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户信息API接口
 */
@RestController
@RequestMapping("/userAPI")
@Slf4j
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 注册用户
     */
    @PostMapping("/registerUser")
    @AccessAuth
    @SentinelResource(SentinelConfig.RESOURCE_USER_API_REGISTER_USER)
    public BaseResponse<Boolean> registerUser(
            @RequestBody @Validated RequestContainer<RegisterUserReq> requestContainer) {
        return success(userService.registerUser(requestContainer));
    }

    /**
     * 获取用户信息列表
     */
    @PostMapping("/getUserList")
    @AccessAuth
    @SentinelResource(SentinelConfig.RESOURCE_USER_API_GET_USER_LIST)
    public BaseResponse<GetUserInfoResp> getUserInfoList(
            @RequestBody @Validated RequestContainer<GetUserInfoReq> requestContainer) {
        return success(userService.getUserInfo(requestContainer));
    }

    /**
     * 封禁多个用户
     */
    @PostMapping("/banUserList")
    @AccessAuth
    @SentinelResource(SentinelConfig.RESOURCE_USER_API_BAN_USER_LIST)
    public BaseResponse<Boolean> banUserList(
            @RequestBody @Validated RequestContainer<BanUserReq> requestContainer) {
        return success(userService.banUser(requestContainer));
    }

    /**
     * 更新用户信息
     */
    @PostMapping("/updateUser")
    @AccessAuth
    @SentinelResource(SentinelConfig.RESOURCE_USER_API_UPDATE_USER)
    public BaseResponse<Boolean> updateUser(
            @RequestBody @Validated RequestContainer<UpdateUserInfoReq> requestContainer) {
        return success(userService.updateUserInfo(requestContainer));
    }

    /**
     * 获取所有用户信息
     */
    @PostMapping("/getAllUserList")
    @AccessAuth
    @SentinelResource(SentinelConfig.RESOURCE_USER_API_ALL_USER)
    public BaseResponse<GetUserInfoResp> getAllUserList(
            @RequestBody @Validated RequestContainer<GetAllUserInfoReq> requestContainer) {
        return success(userService.getAllUserInfo(requestContainer));
    }

}