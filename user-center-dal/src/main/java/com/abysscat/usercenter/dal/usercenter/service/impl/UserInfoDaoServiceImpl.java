package com.abysscat.usercenter.dal.usercenter.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abysscat.usercenter.base.enums.IsDelEnum;
import com.abysscat.usercenter.dal.usercenter.entity.UserInfo;
import com.abysscat.usercenter.dal.usercenter.mapper.UserInfoMapper;
import com.abysscat.usercenter.dal.usercenter.service.UserInfoDaoService;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

@Service
@Slf4j
public class UserInfoDaoServiceImpl implements UserInfoDaoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public Boolean insertOneUserInfo(UserInfo userInfo) {
        if (userInfo == null) {
            return false;
        }
        Date now = new Date();
        userInfo.setCreateTime(now);
        userInfo.setUpdateTime(now);
        return userInfoMapper.insert(userInfo) > 0;
    }

    @Override
    public List<UserInfo> selectUserInfoListByIds(List<Long> userIds) {
        if (CollectionUtils.isEmpty(userIds)) {
            return Lists.newArrayList();
        }
        Example example = new Example(UserInfo.class);
        example.createCriteria()
                .andEqualTo(UserInfo.FIELD_IS_DEL, IsDelEnum.NOT_DELETE)
                .andIn(UserInfo.FIELD_USER_ID, userIds);

        return userInfoMapper.selectByExample(example);
    }

    @Override
    public Boolean deleteUserInfoList(List<Long> userIds, String optUser) {
        if (CollectionUtils.isEmpty(userIds)) {
            return false;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUpdateTime(new Date());
        userInfo.setUpdateUser(optUser);
        userInfo.setIsDel(IsDelEnum.DELETED.getStatus());

        Example example = new Example(UserInfo.class);
        example.createCriteria().andIn(UserInfo.FIELD_USER_ID, userIds);

        return userInfoMapper.updateByExampleSelective(userInfo, example) > 0;
    }

    @Override
    public Boolean updateOneUserInfo(UserInfo userInfo) {
        if (userInfo == null) {
            return false;
        }
        userInfo.setUpdateTime(new Date());

        Example example = new Example(UserInfo.class);
        example.createCriteria()
                .andEqualTo(UserInfo.FIELD_IS_DEL, IsDelEnum.NOT_DELETE)
                .andEqualTo(UserInfo.FIELD_USER_ID, userInfo.getUserId());

        return userInfoMapper.updateByExampleSelective(userInfo, example) > 0;
    }

    @Override
    public List<UserInfo> selectUserInfoListByEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return Lists.newArrayList();
        }
        Example example = new Example(UserInfo.class);
        example.createCriteria()
                .andEqualTo(UserInfo.FIELD_IS_DEL, IsDelEnum.NOT_DELETE)
                .andEqualTo(UserInfo.FIELD_EMAIL, email);

        return userInfoMapper.selectByExample(example);
    }

    @Override
    public List<UserInfo> selectAllUserInfo(Integer pageNum, Integer pageSize) {
        Example example = new Example(UserInfo.class);
        // 默认按更新时间倒序
        example.orderBy(UserInfo.FIELD_UPDATE_TIME).desc();
        example.createCriteria().andEqualTo(UserInfo.FIELD_IS_DEL, IsDelEnum.NOT_DELETE);
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);
        return userInfoMapper.selectByExample(example);
    }
}
