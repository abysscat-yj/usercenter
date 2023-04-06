package com.abysscat.usercenter.sal.msgcenter.mail.sender;

import static com.abysscat.usercenter.sal.msgcenter.mail.sender.EmailSender.SMTP_163;
import static com.abysscat.usercenter.sal.msgcenter.mail.sender.EmailSender.SMTP_ENT_QQ;
import static com.abysscat.usercenter.sal.msgcenter.mail.sender.EmailSender.SMTP_QQ;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.abysscat.usercenter.sal.msgcenter.mail.core.SendMailException;
import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

/**
 * 发送邮件测试
 */
@Ignore
public class EmailSenderTest {

    private static final String TO_EMAIL = "abysscat@gmail.com";

    @Before
    public void before() {
        // 配置，一次即可
        EmailSender.config(SMTP_QQ(false), "xxx@qq.com", "*******");
        // 如果是企业邮箱则使用下面配置
        EmailSender.config(SMTP_ENT_QQ(false), "xxx@qq.com", "*******");
        EmailSender.config(SMTP_163(false), "xxx@163.com", "*******");
    }

    @Test
    public void testSendText() throws SendMailException {
        EmailSender.subject("这是一封测试TEXT邮件")
                .from("abysscat的邮箱")
                .to(TO_EMAIL)
                .text("信件内容")
                .send();
        Assert.assertTrue(true);
    }

    @Test
    public void testSendHtml() throws SendMailException {
        EmailSender.subject("这是一封测试HTML邮件")
                .from("abysscat的邮箱")
                .to(TO_EMAIL)
                .html("<h1 font=red>信件内容</h1>")
                .send();
        Assert.assertTrue(true);
    }

    @Test
    public void testSendAttach() throws SendMailException {
        EmailSender.subject("这是一封测试附件邮件")
                .from("abysscat的邮箱")
                .to(TO_EMAIL)
                .html("<h1 font=red>信件内容</h1>")
                .attach(new File("/Users/abysscat/Downloads/hello.jpeg"), "测试图片.jpeg")
                .send();
        Assert.assertTrue(true);
    }

    @Test
    public void testSendAttachURL() throws SendMailException, MalformedURLException {
        EmailSender.subject("这是一封测试网络资源作为附件的邮件")
                .from("abysscat的邮箱")
                .to(TO_EMAIL)
                .html("<h1 font=red>信件内容</h1>")
                .attachURL(new URL("https://abysscat.com/u/2784452?s=40&v=4"), "测试图片.jpeg")
                .send();
        Assert.assertTrue(true);
    }

    @Test
    public void testPebble() throws IOException, PebbleException, SendMailException {
        PebbleEngine engine = new PebbleEngine.Builder().build();
        PebbleTemplate compiledTemplate = engine.getTemplate("mail/template/register.html");

        Map<String, Object> context = new HashMap<String, Object>();
        context.put("username", "abysscat");
        context.put("email", "abysscat@gmail.me");

        Writer writer = new StringWriter();
        compiledTemplate.evaluate(writer, context);

        String output = writer.toString();
        System.out.println(output);

        EmailSender.subject("这是一封测试Pebble模板邮件")
                .from("abysscat的邮箱")
                .to(TO_EMAIL)
                .html(output)
                .send();
        Assert.assertTrue(true);
    }

}
