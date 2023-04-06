package com.abysscat.usercenter.biz.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abysscat.usercenter.base.asynctask.FutureService;
import com.abysscat.usercenter.base.enums.ErrorEnum;
import com.abysscat.usercenter.base.exception.BusinessException;
import com.abysscat.usercenter.base.web.RequestContainer;
import com.abysscat.usercenter.biz.service.UserService;
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
import com.abysscat.usercenter.sal.msgcenter.mail.vo.RegisterMailReq;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoDaoService userInfoDaoService;

    @Resource(name = "commonFutureService")
    private FutureService futureService;

    @Autowired
    private EmailService emailService;

    @Override
    public Boolean registerUser(RequestContainer<RegisterUserReq> request) {
        RegisterUserReq requestData = request.getData();

        // 通过邮箱校验是否已注册
        List<UserInfo> userInfoList = userInfoDaoService.selectUserInfoListByEmail(requestData.getEmail());
        if (CollectionUtils.isNotEmpty(userInfoList)) {
            throw new BusinessException(ErrorEnum.USER_EXIST_ERROR);
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(IdUtil.getSnowflakeNextId());
        userInfo.setAvatar(requestData.getAvatar());
        userInfo.setNickname(requestData.getNickname());
        userInfo.setGender(requestData.getGender());
        userInfo.setEmail(requestData.getEmail());
        userInfo.setCreateUser(request.getUsername());
        userInfo.setUpdateUser(request.getUsername());

        Boolean result = userInfoDaoService.insertOneUserInfo(userInfo);
        if (BooleanUtils.isNotTrue(result)) {
            throw new BusinessException(ErrorEnum.INSERT_USER_ERROR);
        }

        // 异步发送注册成功邮件，后期可通过消息队列进一步优化
        futureService.runAsyncTask(() -> sendRegisterMail(userInfo));

        return true;
    }

    private void sendRegisterMail(UserInfo userInfo) {
        RegisterMailReq mailReq = new RegisterMailReq();
        mailReq.setNickname(userInfo.getNickname());
        mailReq.setUserEmail(userInfo.getEmail());
        try {
            emailService.SendRegisterMail(mailReq);
        } catch (Exception e) {
            log.error("UserServiceImpl.registerUser SendRegisterMail failed. ERROR:", e);
        }
    }

    @Override
    public GetUserInfoResp getUserInfo(RequestContainer<GetUserInfoReq> request) {
        GetUserInfoReq requestData = request.getData();
        if (CollectionUtils.isEmpty(requestData.getUserIds())) {
            throw new BusinessException(ErrorEnum.PARAM_ERROR);
        }

        GetUserInfoResp getUserInfoResp = new GetUserInfoResp();

        List<UserInfo> userInfoList = userInfoDaoService.selectUserInfoListByIds(requestData.getUserIds());

        if (CollectionUtils.isEmpty(userInfoList)) {
            getUserInfoResp.setUserInfoList(Collections.EMPTY_LIST);
            return getUserInfoResp;
        }

        // 将DB用户对象转化为前端展示用户对象
        List<BaseUserInfoVO> userInfoVOList = userInfoList.stream()
                .map(userInfo -> transferUserInfoToVO(userInfo))
                .collect(Collectors.toList());

        getUserInfoResp.setUserInfoList(userInfoVOList);

        return getUserInfoResp;
    }


    @Override
    public Boolean banUser(RequestContainer<BanUserReq> request) {
        BanUserReq requestData = request.getData();
        if (CollectionUtils.isEmpty(requestData.getUserIds())) {
            throw new BusinessException(ErrorEnum.PARAM_ERROR);
        }
        return userInfoDaoService.deleteUserInfoList(requestData.getUserIds(), request.getUsername());
    }

    @Override
    public Boolean updateUserInfo(RequestContainer<UpdateUserInfoReq> request) {
        UpdateUserInfoReq requestData = request.getData();

        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(requestData.getUserId());
        userInfo.setAvatar(requestData.getAvatar());
        userInfo.setEmail(requestData.getEmail());
        userInfo.setGender(requestData.getGender());
        userInfo.setUpdateUser(request.getUsername());

        return userInfoDaoService.updateOneUserInfo(userInfo);
    }

    @Override
    public GetUserInfoResp getAllUserInfo(RequestContainer<GetAllUserInfoReq> request) {
        GetAllUserInfoReq requestData = request.getData();

        List<UserInfo> userInfoList =
                userInfoDaoService.selectAllUserInfo(requestData.getPageNum(), requestData.getPageSize());

        GetUserInfoResp getUserInfoResp = new GetUserInfoResp();

        if (CollectionUtils.isEmpty(userInfoList)) {
            getUserInfoResp.setUserInfoList(Collections.EMPTY_LIST);
            return getUserInfoResp;
        }

        List<BaseUserInfoVO> userInfoVOList = userInfoList.stream()
                .map(userInfo -> transferUserInfoToVO(userInfo))
                .collect(Collectors.toList());

        getUserInfoResp.setUserInfoList(userInfoVOList);

        return getUserInfoResp;
    }

    private BaseUserInfoVO transferUserInfoToVO(UserInfo userInfo) {
        BaseUserInfoVO userInfoVO = new BaseUserInfoVO();
        userInfoVO.setUserId(userInfo.getUserId());
        userInfoVO.setAvatar(userInfo.getAvatar());
        userInfoVO.setGender(userInfo.getGender());
        userInfoVO.setEmail(userInfo.getEmail());
        userInfoVO.setNickname(userInfo.getNickname());
        return userInfoVO;
    }
}
