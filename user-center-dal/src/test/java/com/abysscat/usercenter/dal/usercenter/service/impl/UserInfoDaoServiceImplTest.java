package com.abysscat.usercenter.dal.usercenter.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.abysscat.usercenter.dal.usercenter.entity.UserInfo;
import com.abysscat.usercenter.dal.usercenter.mapper.UserInfoMapper;

import tk.mybatis.mapper.entity.Config;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

@RunWith(MockitoJUnitRunner.class)
public class UserInfoDaoServiceImplTest {

    @Mock
    private UserInfoMapper mockUserInfoMapper;

    @InjectMocks
    private UserInfoDaoServiceImpl userInfoDaoServiceImplUnderTest;

    @Before
    public void init() {
        ReflectionTestUtils.setField(userInfoDaoServiceImplUnderTest,
                "userInfoMapper", mockUserInfoMapper);
        EntityHelper.initEntityNameMap(UserInfo.class, new Config());
    }

    @Test
    public void testInsertOneUserInfo() {
        // Setup
        final UserInfo userInfo = new UserInfo();
        userInfo.setId(0L);
        userInfo.setUserId(0L);
        userInfo.setAvatar("avatar");
        userInfo.setNickname("nickname");
        userInfo.setGender(0);
        userInfo.setEmail("email");
        userInfo.setCreateTime(new Date());
        userInfo.setCreateUser("createUser");
        userInfo.setUpdateTime(new Date());
        userInfo.setUpdateUser("optUser");
        userInfo.setIsDel(0);

        when(mockUserInfoMapper.insert(any())).thenReturn(0);

        // Run the test
        final Boolean result1 = userInfoDaoServiceImplUnderTest.insertOneUserInfo(userInfo);
        // Verify the results
        assertThat(result1).isFalse();

        // Run the test
        final Boolean result2 = userInfoDaoServiceImplUnderTest.insertOneUserInfo(null);
        // Verify the results
        assertThat(result2).isFalse();
    }

    @Test
    public void testSelectUserInfoListByIds() {
        // Setup
        final UserInfo userInfo = new UserInfo();
        userInfo.setId(0L);
        userInfo.setUserId(0L);
        userInfo.setAvatar("avatar");
        userInfo.setNickname("nickname");
        userInfo.setGender(0);
        userInfo.setEmail("email");
        userInfo.setCreateTime(new Date());
        userInfo.setCreateUser("createUser");
        userInfo.setUpdateTime(new Date());
        userInfo.setUpdateUser("optUser");
        userInfo.setIsDel(0);
        final List<UserInfo> expectedResult = Arrays.asList(userInfo);

        // Configure UserInfoMapper.selectByExample(...).
        final UserInfo userInfo1 = new UserInfo();
        userInfo1.setId(0L);
        userInfo1.setUserId(0L);
        userInfo1.setAvatar("avatar");
        userInfo1.setNickname("nickname");
        userInfo1.setGender(0);
        userInfo1.setEmail("email");
        userInfo1.setCreateTime(new Date());
        userInfo1.setCreateUser("createUser");
        userInfo1.setUpdateTime(new Date());
        userInfo1.setUpdateUser("optUser");
        userInfo1.setIsDel(0);
        final List<UserInfo> userInfoList = Arrays.asList(userInfo1);
        when(mockUserInfoMapper.selectByExample(any(Object.class))).thenReturn(userInfoList);

        // Run the test
        final List<UserInfo> result = userInfoDaoServiceImplUnderTest.selectUserInfoListByIds(Arrays.asList(0L));
        // Verify the results
        assertThat(result).isEqualTo(expectedResult);

        // Run the test
        final List<UserInfo> result2 = userInfoDaoServiceImplUnderTest.selectUserInfoListByIds(null);
        // Verify the results
        assertThat(result2).isEqualTo(Lists.newArrayList());
    }

    @Test
    public void testSelectUserInfoListByIds_UserInfoMapperReturnsNoItems() {
        // Setup
        when(mockUserInfoMapper.selectByExample(any(Object.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<UserInfo> result = userInfoDaoServiceImplUnderTest.selectUserInfoListByIds(Arrays.asList(0L));

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    public void testDeleteUserInfoList() {
        // Setup
        when(mockUserInfoMapper.updateByExampleSelective(any(), any())).thenReturn(0);

        // Run the test
        final Boolean result = userInfoDaoServiceImplUnderTest.deleteUserInfoList(Arrays.asList(0L), "optUser");
        // Verify the results
        assertThat(result).isFalse();

        // Run the test
        final Boolean result2 = userInfoDaoServiceImplUnderTest.deleteUserInfoList(null, "optUser");
        // Verify the results
        assertThat(result2).isFalse();
    }

    @Test
    public void testUpdateOneUserInfo() {
        // Setup
        final UserInfo userInfo = new UserInfo();
        userInfo.setId(0L);
        userInfo.setUserId(0L);
        userInfo.setAvatar("avatar");
        userInfo.setNickname("nickname");
        userInfo.setGender(0);
        userInfo.setEmail("email");
        userInfo.setCreateTime(new Date());
        userInfo.setCreateUser("createUser");
        userInfo.setUpdateTime(new Date());
        userInfo.setUpdateUser("optUser");
        userInfo.setIsDel(0);

        when(mockUserInfoMapper.updateByExampleSelective(any(), any())).thenReturn(0);

        // Run the test
        final Boolean result = userInfoDaoServiceImplUnderTest.updateOneUserInfo(userInfo);
        // Verify the results
        assertThat(result).isFalse();

        // Run the test
        final Boolean result2 = userInfoDaoServiceImplUnderTest.updateOneUserInfo(null);
        // Verify the results
        assertThat(result2).isFalse();
    }

    @Test
    public void testSelectUserInfoListByEmail() {
        // Setup
        final UserInfo userInfo = new UserInfo();
        userInfo.setId(0L);
        userInfo.setUserId(0L);
        userInfo.setAvatar("avatar");
        userInfo.setNickname("nickname");
        userInfo.setGender(0);
        userInfo.setEmail("email");
        userInfo.setCreateTime(new Date());
        userInfo.setCreateUser("createUser");
        userInfo.setUpdateTime(new Date());
        userInfo.setUpdateUser("optUser");
        userInfo.setIsDel(0);
        final List<UserInfo> expectedResult = Arrays.asList(userInfo);

        // Configure UserInfoMapper.selectByExample(...).
        final UserInfo userInfo1 = new UserInfo();
        userInfo1.setId(0L);
        userInfo1.setUserId(0L);
        userInfo1.setAvatar("avatar");
        userInfo1.setNickname("nickname");
        userInfo1.setGender(0);
        userInfo1.setEmail("email");
        userInfo1.setCreateTime(new Date());
        userInfo1.setCreateUser("createUser");
        userInfo1.setUpdateTime(new Date());
        userInfo1.setUpdateUser("optUser");
        userInfo1.setIsDel(0);
        final List<UserInfo> userInfoList = Arrays.asList(userInfo1);
        when(mockUserInfoMapper.selectByExample(any(Object.class))).thenReturn(userInfoList);

        // Run the test
        final List<UserInfo> result = userInfoDaoServiceImplUnderTest.selectUserInfoListByEmail("email");
        // Verify the results
        assertThat(result).isEqualTo(expectedResult);

        // Run the test
        final List<UserInfo> result2 = userInfoDaoServiceImplUnderTest.selectUserInfoListByEmail(null);
        // Verify the results
        assertThat(result2).isEqualTo(Lists.newArrayList());
    }

    @Test
    public void testSelectUserInfoListByEmail_UserInfoMapperReturnsNoItems() {
        // Setup
        when(mockUserInfoMapper.selectByExample(any(Object.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<UserInfo> result = userInfoDaoServiceImplUnderTest.selectUserInfoListByEmail("email");

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    public void testSelectAllUserInfo() {
        // Setup
        final UserInfo userInfo = new UserInfo();
        userInfo.setId(0L);
        userInfo.setUserId(0L);
        userInfo.setAvatar("avatar");
        userInfo.setNickname("nickname");
        userInfo.setGender(0);
        userInfo.setEmail("email");
        userInfo.setCreateTime(new Date());
        userInfo.setCreateUser("createUser");
        userInfo.setUpdateTime(new Date());
        userInfo.setUpdateUser("optUser");
        userInfo.setIsDel(0);
        final List<UserInfo> expectedResult = Arrays.asList(userInfo);

        // Configure UserInfoMapper.selectByExample(...).
        final UserInfo userInfo1 = new UserInfo();
        userInfo1.setId(0L);
        userInfo1.setUserId(0L);
        userInfo1.setAvatar("avatar");
        userInfo1.setNickname("nickname");
        userInfo1.setGender(0);
        userInfo1.setEmail("email");
        userInfo1.setCreateTime(new Date());
        userInfo1.setCreateUser("createUser");
        userInfo1.setUpdateTime(new Date());
        userInfo1.setUpdateUser("optUser");
        userInfo1.setIsDel(0);
        final List<UserInfo> userInfoList = Arrays.asList(userInfo1);
        when(mockUserInfoMapper.selectByExample(any(Object.class))).thenReturn(userInfoList);

        // Run the test
        final List<UserInfo> result = userInfoDaoServiceImplUnderTest.selectAllUserInfo(0, 0);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testSelectAllUserInfo_UserInfoMapperReturnsNoItems() {
        // Setup
        when(mockUserInfoMapper.selectByExample(any(Object.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<UserInfo> result = userInfoDaoServiceImplUnderTest.selectAllUserInfo(0, 0);

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }
}
