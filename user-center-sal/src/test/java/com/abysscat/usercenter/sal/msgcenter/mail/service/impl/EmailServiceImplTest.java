package com.abysscat.usercenter.sal.msgcenter.mail.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.abysscat.usercenter.base.enums.ErrorEnum;
import com.abysscat.usercenter.base.exception.BusinessException;
import com.abysscat.usercenter.sal.msgcenter.mail.core.SendMailException;
import com.abysscat.usercenter.sal.msgcenter.mail.vo.RegisterMailReq;

public class EmailServiceImplTest {

    private EmailServiceImpl emailServiceImplUnderTest;

    @Before
    public void setUp() {
        emailServiceImplUnderTest = new EmailServiceImpl();
    }

    @Test
    public void testInit() {
        emailServiceImplUnderTest.init();
    }

    @Test
    public void testSendRegisterMail_throwException() {
        // Setup
        final RegisterMailReq mailReq = new RegisterMailReq();
        mailReq.setNickname("nickname");
        mailReq.setUserEmail("userEmail");

        // Run the test
        try {
            emailServiceImplUnderTest.SendRegisterMail(mailReq);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(SendMailException.class);
            return;
        }
        Assert.fail();
    }

    @Test
    public void testSendRegisterMail_paramError() {
        // Run the test
        try {
            emailServiceImplUnderTest.SendRegisterMail(null);
        } catch (Exception e) {
            assertThat(e).isEqualToComparingFieldByField(new BusinessException(ErrorEnum.PARAM_ERROR));
            return;
        }
        Assert.fail();
    }
}
