package com.abysscat.usercenter.sal.msgcenter.mail.service.impl;

import static com.abysscat.usercenter.sal.msgcenter.mail.sender.EmailSender.SMTP_QQ;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.abysscat.usercenter.base.enums.ErrorEnum;
import com.abysscat.usercenter.base.exception.BusinessException;
import com.abysscat.usercenter.sal.msgcenter.mail.sender.EmailSender;
import com.abysscat.usercenter.sal.msgcenter.mail.service.EmailService;
import com.abysscat.usercenter.sal.msgcenter.mail.vo.RegisterMailReq;
import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @PostConstruct
    public void init() {
        // 配置邮箱服务账号密码， 可放到配置文件
        EmailSender.config(SMTP_QQ(false), "xxx@qq.com", "*******");
    }

    @Override
    public void SendRegisterMail(RegisterMailReq mailReq) throws Exception {
        if (mailReq == null || StringUtils.isAnyBlank(mailReq.getNickname(), mailReq.getUserEmail())) {
            throw new BusinessException(ErrorEnum.PARAM_ERROR);
        }
        // 构造注册成功邮件模板
        PebbleEngine engine = new PebbleEngine.Builder().build();
        PebbleTemplate compiledTemplate = engine.getTemplate("mail/template/register.html");

        Map<String, Object> context = new HashMap<>();
        context.put("username", mailReq.getNickname());
        context.put("email", "abysscat@gmail.com");

        Writer writer = new StringWriter();
        compiledTemplate.evaluate(writer, context);
        String output = writer.toString();

        EmailSender.subject("abysscat服务注册邮件")
                .from("abysscat")
                .to(mailReq.getUserEmail())
                .html(output)
                .send();
    }

}
