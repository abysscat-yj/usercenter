package com.abysscat.usercenter.dal.usercenter.service;

import java.util.List;

import com.abysscat.usercenter.dal.usercenter.entity.UserInfo;

/**
 * 用户信息DB操作服务类
 *
 * 注：业务层必须调用该接口，隔离DB资源依赖，禁止直接调用mapper
 */
public interface UserInfoDaoService {

    /**
     * 添加单个用户
     * @param userInfo 单个用户实体
     * @return 是否添加成功
     */
    Boolean insertOneUserInfo(UserInfo userInfo);

    /**
     * 查询用户信息列表
     * @param userIds 用户id列表
     * @return 用户信息列表
     */
    List<UserInfo> selectUserInfoListByIds(List<Long> userIds);

    /**
     * 逻辑删除多个用户
     * @param userIds 用户id列表
     * @param optUser 操作者
     * @return 是否删除成功
     */
    Boolean deleteUserInfoList(List<Long> userIds, String optUser);

    /**
     * 更新单个用户信息
     * @param userInfo 待更新的用户信息
     * @return 是否更新成功
     */
    Boolean updateOneUserInfo(UserInfo userInfo);

    /**
     * 根据邮箱查询用户信息
     * @param email 用户邮箱
     * @return 用户信息集合
     */
    List<UserInfo> selectUserInfoListByEmail(String email);

    /**
     * 获取所有用户信息
     * @param pageNum 页码
     * @param pageSize 页面大小
     * @return 所有用户信息
     */
    List<UserInfo> selectAllUserInfo(Integer pageNum, Integer pageSize);

}
