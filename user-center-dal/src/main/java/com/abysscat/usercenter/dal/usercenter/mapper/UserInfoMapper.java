package com.abysscat.usercenter.dal.usercenter.mapper;

import com.abysscat.usercenter.dal.config.MyBatisUserCenterDao;
import com.abysscat.usercenter.dal.config.mapper.BaseMapper;
import com.abysscat.usercenter.dal.usercenter.entity.UserInfo;

/**
* UserInfo DB 直接操作类
*/
@MyBatisUserCenterDao
public interface UserInfoMapper extends BaseMapper<UserInfo> {

}
