package com.abysscat.usercenter.biz.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.abysscat.usercenter.base.asynctask.FutureService;
import com.abysscat.usercenter.base.enums.ErrorEnum;
import com.abysscat.usercenter.base.exception.BusinessException;
import com.abysscat.usercenter.base.web.RequestContainer;
import com.abysscat.usercenter.biz.vo.BanUserReq;
import com.abysscat.usercenter.biz.vo.BaseUserInfoVO;
import com.abysscat.usercenter.biz.vo.GetAllUserInfoReq;
import com.abysscat.usercenter.biz.vo.GetUserInfoReq;
import com.abysscat.usercenter.biz.vo.GetUserInfoResp;
import com.abysscat.usercenter.biz.vo.RegisterUserReq;
import com.abysscat.usercenter.biz.vo.UpdateUserInfoReq;
import com.abysscat.usercenter.dal.usercenter.entity.UserInfo;
import com.abysscat.usercenter.dal.usercenter.service.UserInfoDaoService;
import com.abysscat.usercenter.sal.msgcenter.mail.service.EmailService;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserInfoDaoService mockUserInfoDaoService;
    @Mock
    private FutureService mockFutureService;
    @Mock
    private EmailService mockEmailService;

    @InjectMocks
    private UserServiceImpl userServiceImplUnderTest;

    @Test
    public void testRegisterUser() {
        // Setup
        final RequestContainer<RegisterUserReq> request = new RequestContainer<>();
        request.setReqId("reqId");
        request.setAppId("appId");
        request.setClientIp("127.0.0.1");
        request.setUsername("abysscat");
        final RegisterUserReq registerUserReq = new RegisterUserReq();
        registerUserReq.setAvatar("https://abysscat.png");
        registerUserReq.setNickname("abysscat");
        registerUserReq.setGender(0);
        registerUserReq.setEmail("abysscat@163.com");
        request.setData(registerUserReq);

        // Configure UserInfoDaoService.selectUserInfoListByEmail(...).
        final UserInfo userInfo = new UserInfo();
        userInfo.setId(0L);
        userInfo.setUserId(0L);
        userInfo.setAvatar("https://abysscat.png");
        userInfo.setNickname("abysscat");
        userInfo.setGender(0);
        userInfo.setEmail("abysscat@163.com");
        userInfo.setCreateTime(new Date());
        userInfo.setCreateUser("abysscat");
        userInfo.setUpdateTime(new Date());
        userInfo.setUpdateUser("abysscat");
        userInfo.setIsDel(0);
        final List<UserInfo> userInfoList = Arrays.asList(userInfo);

        when(mockUserInfoDaoService.selectUserInfoListByEmail(any())).thenReturn(null);

        when(mockUserInfoDaoService.insertOneUserInfo(any())).thenReturn(true);
        doAnswer(invocation -> {
            ((Runnable) invocation.getArguments()[0]).run();
            return CompletableFuture.completedFuture(null);
        }).when(mockFutureService).runAsyncTask(any(Runnable.class));

        // Run the test
        final Boolean result = userServiceImplUnderTest.registerUser(request);
        // Verify the results
        assertThat(result).isTrue();


        when(mockUserInfoDaoService.selectUserInfoListByEmail(any())).thenReturn(Lists.newArrayList(userInfo));
        // Run the test
        try {
            userServiceImplUnderTest.registerUser(request);
        } catch (Exception e) {
            // Verify the results
            assertThat(e).isEqualToComparingFieldByField(new BusinessException(ErrorEnum.USER_EXIST_ERROR));
            return;
        }
        Assert.fail();
    }

    @Test
    public void testRegisterUser_UserInfoDaoServiceSelectUserInfoListByEmailReturnsNoItems() {
        // Setup
        final RequestContainer<RegisterUserReq> request = new RequestContainer<>();
        request.setReqId("reqId");
        request.setAppId("appId");
        request.setClientIp("127.0.0.1");
        request.setUsername("abysscat");
        final RegisterUserReq registerUserReq = new RegisterUserReq();
        registerUserReq.setAvatar("https://abysscat.png");
        registerUserReq.setNickname("abysscat");
        registerUserReq.setGender(0);
        registerUserReq.setEmail("abysscat@163.com");
        request.setData(registerUserReq);

        when(mockUserInfoDaoService.selectUserInfoListByEmail("abysscat@163.com")).thenReturn(Collections.emptyList());
        when(mockUserInfoDaoService.insertOneUserInfo(any())).thenReturn(false);

        // Run the test
        try {
            userServiceImplUnderTest.registerUser(request);
        } catch (Exception e) {
            // Verify the results
            assertThat(e).isEqualToComparingFieldByField(new BusinessException(ErrorEnum.INSERT_USER_ERROR));
            return;
        }
        Assert.fail();
    }

    @Test
    public void testRegisterUser_FutureServiceReturnsFailure() {
        // Setup
        final RequestContainer<RegisterUserReq> request = new RequestContainer<>();
        request.setReqId("reqId");
        request.setAppId("appId");
        request.setClientIp("127.0.0.1");
        request.setUsername("abysscat");
        final RegisterUserReq registerUserReq = new RegisterUserReq();
        registerUserReq.setAvatar("https://abysscat.png");
        registerUserReq.setNickname("abysscat");
        registerUserReq.setGender(0);
        registerUserReq.setEmail("abysscat@163.com");
        request.setData(registerUserReq);

        when(mockUserInfoDaoService.selectUserInfoListByEmail("abysscat@163.com")).thenReturn(null);

        when(mockUserInfoDaoService.insertOneUserInfo(any())).thenReturn(true);

        // Configure FutureService.runAsyncTask(...).
        final CompletableFuture<Void> voidCompletableFuture = new CompletableFuture<>();
        voidCompletableFuture.completeExceptionally(new Exception("message"));
        when(mockFutureService.runAsyncTask(any(Runnable.class))).thenReturn(voidCompletableFuture);

        // Run the test
        final Boolean result = userServiceImplUnderTest.registerUser(request);

        // Verify the results
        assertThat(result).isTrue();
    }

    @Test
    public void testGetUserInfo() {
        // Setup
        final RequestContainer<GetUserInfoReq> request = new RequestContainer<>();
        request.setReqId("reqId");
        request.setAppId("appId");
        request.setClientIp("127.0.0.1");
        request.setUsername("abysscat");
        final GetUserInfoReq getUserInfoReq = new GetUserInfoReq();
        getUserInfoReq.setUserIds(Arrays.asList(0L));
        request.setData(getUserInfoReq);

        final GetUserInfoResp expectedResult = new GetUserInfoResp();
        final BaseUserInfoVO baseUserInfoVO = new BaseUserInfoVO();
        baseUserInfoVO.setUserId(0L);
        baseUserInfoVO.setAvatar("https://abysscat.png");
        baseUserInfoVO.setNickname("abysscat");
        baseUserInfoVO.setGender(0);
        baseUserInfoVO.setEmail("abysscat@163.com");
        expectedResult.setUserInfoList(Arrays.asList(baseUserInfoVO));

        // Configure UserInfoDaoService.selectUserInfoListByIds(...).
        final UserInfo userInfo = new UserInfo();
        userInfo.setId(0L);
        userInfo.setUserId(0L);
        userInfo.setAvatar("https://abysscat.png");
        userInfo.setNickname("abysscat");
        userInfo.setGender(0);
        userInfo.setEmail("abysscat@163.com");
        userInfo.setCreateTime(new Date());
        userInfo.setCreateUser("abysscat");
        userInfo.setUpdateTime(new Date());
        userInfo.setUpdateUser("abysscat");
        userInfo.setIsDel(0);
        final List<UserInfo> userInfoList = Arrays.asList(userInfo);
        when(mockUserInfoDaoService.selectUserInfoListByIds(Arrays.asList(0L))).thenReturn(userInfoList);

        // Run the test
        final GetUserInfoResp result = userServiceImplUnderTest.getUserInfo(request);
        // Verify the results
        assertThat(result).isNotNull();
        assertThat(result.getUserInfoList()).isNotNull();
        assertThat(result.getUserInfoList().size()).isEqualTo(1);


        // Run the test
        getUserInfoReq.setUserIds(null);
        try {
            userServiceImplUnderTest.getUserInfo(request);
        } catch (Exception e) {
            // Verify the results
            assertThat(e).isEqualToComparingFieldByField(new BusinessException(ErrorEnum.PARAM_ERROR));
            return;
        }
        Assert.fail();
    }

    @Test
    public void testGetUserInfo_UserInfoDaoServiceReturnsNoItems() {
        // Setup
        final RequestContainer<GetUserInfoReq> request = new RequestContainer<>();
        request.setReqId("reqId");
        request.setAppId("appId");
        request.setClientIp("127.0.0.1");
        request.setUsername("abysscat");
        final GetUserInfoReq getUserInfoReq = new GetUserInfoReq();
        getUserInfoReq.setUserIds(Arrays.asList(0L));
        request.setData(getUserInfoReq);

        final GetUserInfoResp expectedResult = new GetUserInfoResp();
        expectedResult.setUserInfoList(Lists.newArrayList());

        when(mockUserInfoDaoService.selectUserInfoListByIds(Arrays.asList(0L))).thenReturn(Collections.emptyList());

        // Run the test
        final GetUserInfoResp result = userServiceImplUnderTest.getUserInfo(request);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testBanUser() {
        // Setup
        final RequestContainer<BanUserReq> request = new RequestContainer<>();
        request.setReqId("reqId");
        request.setAppId("appId");
        request.setClientIp("127.0.0.1");
        request.setUsername("abysscat");
        final BanUserReq banUserReq = new BanUserReq();
        banUserReq.setUserIds(Arrays.asList(0L));
        request.setData(banUserReq);

        when(mockUserInfoDaoService.deleteUserInfoList(Arrays.asList(0L), "abysscat")).thenReturn(false);

        // Run the test
        final Boolean result = userServiceImplUnderTest.banUser(request);
        // Verify the results
        assertThat(result).isFalse();


        banUserReq.setUserIds(null);
        try {
            userServiceImplUnderTest.banUser(request);
        } catch (Exception e) {
            // Verify the results
            assertThat(e).isEqualToComparingFieldByField(new BusinessException(ErrorEnum.PARAM_ERROR));
            return;
        }
        Assert.fail();
    }

    @Test
    public void testUpdateUserInfo() {
        // Setup
        final RequestContainer<UpdateUserInfoReq> request = new RequestContainer<>();
        request.setReqId("reqId");
        request.setAppId("appId");
        request.setClientIp("127.0.0.1");
        request.setUsername("abysscat");
        final UpdateUserInfoReq updateUserInfoReq = new UpdateUserInfoReq();
        updateUserInfoReq.setUserId(0L);
        updateUserInfoReq.setAvatar("https://abysscat.png");
        updateUserInfoReq.setNickname("abysscat");
        updateUserInfoReq.setGender(0);
        updateUserInfoReq.setEmail("abysscat@163.com");
        request.setData(updateUserInfoReq);

        when(mockUserInfoDaoService.updateOneUserInfo(any())).thenReturn(false);

        // Run the test
        final Boolean result = userServiceImplUnderTest.updateUserInfo(request);

        // Verify the results
        assertThat(result).isFalse();
    }

    @Test
    public void testGetAllUserInfo() {
        // Setup
        final RequestContainer<GetAllUserInfoReq> request = new RequestContainer<>();
        request.setReqId("reqId");
        request.setAppId("appId");
        request.setClientIp("127.0.0.1");
        request.setUsername("abysscat");
        final GetAllUserInfoReq getAllUserInfoReq = new GetAllUserInfoReq();
        getAllUserInfoReq.setPageNum(0);
        getAllUserInfoReq.setPageSize(0);
        request.setData(getAllUserInfoReq);

        final GetUserInfoResp expectedResult = new GetUserInfoResp();
        final BaseUserInfoVO baseUserInfoVO = new BaseUserInfoVO();
        baseUserInfoVO.setUserId(0L);
        baseUserInfoVO.setAvatar("https://abysscat.png");
        baseUserInfoVO.setNickname("abysscat");
        baseUserInfoVO.setGender(0);
        baseUserInfoVO.setEmail("abysscat@163.com");
        expectedResult.setUserInfoList(Arrays.asList(baseUserInfoVO));

        // Configure UserInfoDaoService.selectAllUserInfo(...).
        final UserInfo userInfo = new UserInfo();
        userInfo.setId(0L);
        userInfo.setUserId(0L);
        userInfo.setAvatar("https://abysscat.png");
        userInfo.setNickname("abysscat");
        userInfo.setGender(0);
        userInfo.setEmail("abysscat@163.com");
        userInfo.setCreateTime(new Date());
        userInfo.setCreateUser("abysscat");
        userInfo.setUpdateTime(new Date());
        userInfo.setUpdateUser("abysscat");
        userInfo.setIsDel(0);
        final List<UserInfo> userInfoList = Arrays.asList(userInfo);
        when(mockUserInfoDaoService.selectAllUserInfo(0, 0)).thenReturn(userInfoList);

        // Run the test
        final GetUserInfoResp result = userServiceImplUnderTest.getAllUserInfo(request);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGetAllUserInfo_UserInfoDaoServiceReturnsNoItems() {
        // Setup
        final RequestContainer<GetAllUserInfoReq> request = new RequestContainer<>();
        request.setReqId("reqId");
        request.setAppId("appId");
        request.setClientIp("127.0.0.1");
        request.setUsername("abysscat");
        final GetAllUserInfoReq getAllUserInfoReq = new GetAllUserInfoReq();
        getAllUserInfoReq.setPageNum(0);
        getAllUserInfoReq.setPageSize(0);
        request.setData(getAllUserInfoReq);

        final GetUserInfoResp expectedResult = new GetUserInfoResp();
        expectedResult.setUserInfoList(Lists.newArrayList());

        when(mockUserInfoDaoService.selectAllUserInfo(0, 0)).thenReturn(Collections.emptyList());

        // Run the test
        final GetUserInfoResp result = userServiceImplUnderTest.getAllUserInfo(request);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

}
