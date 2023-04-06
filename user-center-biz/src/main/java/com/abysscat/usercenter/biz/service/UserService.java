package com.abysscat.usercenter.biz.service;

import com.abysscat.usercenter.base.web.RequestContainer;
import com.abysscat.usercenter.biz.vo.BanUserReq;
import com.abysscat.usercenter.biz.vo.GetAllUserInfoReq;
import com.abysscat.usercenter.biz.vo.GetUserInfoReq;
import com.abysscat.usercenter.biz.vo.GetUserInfoResp;
import com.abysscat.usercenter.biz.vo.RegisterUserReq;
import com.abysscat.usercenter.biz.vo.UpdateUserInfoReq;

/**
 * 用户核心服务接口
 */
public interface UserService {

    /**
     * 注册单个用户
     * @param requestContainer 用户录入信息
     * @return 是否注册成功
     */
    Boolean registerUser(RequestContainer<RegisterUserReq> requestContainer);

    /**
     * 获取多个用户信息
     * @param requestContainer 获取用户信息参数
     * @return 用户信息集合
     */
    GetUserInfoResp getUserInfo(RequestContainer<GetUserInfoReq> requestContainer);

    /**
     * 根据用户ID集合批量封禁/软删除用户
     * @param requestContainer 封禁用户参数
     * @return 是否封禁成功
     */
    Boolean banUser(RequestContainer<BanUserReq> requestContainer);

    /**
     * 更新单个用户信息
     * @param requestContainer 更新用户信息参数
     * @return 是否更新成功
     */
    Boolean updateUserInfo(RequestContainer<UpdateUserInfoReq> requestContainer);

    /**
     * 获取所有用户信息
     * @param requestContainer 基础筛选参数
     * @return 所有用户信息
     */
    GetUserInfoResp getAllUserInfo(RequestContainer<GetAllUserInfoReq> requestContainer);

}
