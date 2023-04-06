package com.abysscat.usercenter.sal.msgcenter.mail.sender;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.abysscat.usercenter.sal.msgcenter.mail.core.SendMailException;

/**
 * 邮件发送服务（后续邮件服务独立后可走RPC调用）
 */
public class EmailSender {

    private static Session session;
    private static String  user;

    private MimeMessage msg;
    private String text;
    private String html;
    private List<MimeBodyPart> attachments = new ArrayList<>();

    private EmailSender() {
    }

    /**
     * 默认配置
     */
    public static Properties defaultConfig(Boolean debug) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.debug", null != debug ? debug.toString() : "false");
        props.put("mail.smtp.timeout", "10000");
        props.put("mail.smtp.port", "465");
        return props;
    }

    /**
     * smtp entnterprise qq
     *
     * @param debug
     * @return
     */
    public static Properties SMTP_ENT_QQ(boolean debug) {
        Properties props = defaultConfig(debug);
        props.put("mail.smtp.host", "smtp.exmail.qq.com");
        return props;
    }

    /**
     * smtp qq
     *
     * @param debug enable debug
     * @return
     */
    public static Properties SMTP_QQ(boolean debug) {
        Properties props = defaultConfig(debug);
        props.put("mail.smtp.host", "smtp.qq.com");
        return props;
    }

    /**
     * smtp 163
     *
     * @param debug enable debug
     * @return
     */
    public static Properties SMTP_163(Boolean debug) {
        Properties props = defaultConfig(debug);
        props.put("mail.smtp.host", "smtp.163.com");
        return props;
    }

    /**
     * 配置用户名和密码
     *
     * @param props    email property config
     * @param username email auth username
     * @param password email auth password
     */
    public static void config(Properties props, final String username, final String password) {
        props.setProperty("username", username);
        props.setProperty("password", password);
        config(props);
    }

    public static void config(Properties props) {
        final String username = props.getProperty("username");
        final String password = props.getProperty("password");
        user = username;
        session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    /**
     * 设置邮件主题
     *
     * @param subject subject title
     */
    public static EmailSender subject(String subject) throws SendMailException {
        EmailSender EmailSender = new EmailSender();
        EmailSender.msg = new MimeMessage(session);
        try {
            EmailSender.msg.setSubject(subject, "UTF-8");
        } catch (Exception e) {
            throw new SendMailException(e);
        }
        return EmailSender;
    }

    /**
     * 设置邮件from对象
     *
     * @param nickName from nickname
     */
    public EmailSender from(String nickName) throws SendMailException {
        return from(nickName, user);
    }

    /**
     * 设置邮件昵称和from对象
     *
     * @param nickName from nickname
     * @param from     from email
     */
    public EmailSender from(String nickName, String from) throws SendMailException {
        try {
            String encodeNickName = MimeUtility.encodeText(nickName);
            msg.setFrom(new InternetAddress(encodeNickName + " <" + from + ">"));
        } catch (Exception e) {
            throw new SendMailException(e);
        }
        return this;
    }

    public EmailSender to(String to) throws SendMailException {
        try {
            return addRecipient(to, Message.RecipientType.TO);
        } catch (MessagingException e) {
            throw new SendMailException(e);
        }
    }

    private EmailSender addRecipient(String recipient, Message.RecipientType type) throws MessagingException {
        msg.setRecipients(type, InternetAddress.parse(recipient.replace(";", ",")));
        return this;
    }

    public EmailSender text(String text) {
        this.text = text;
        return this;
    }

    public EmailSender html(String html) {
        this.html = html;
        return this;
    }

    public EmailSender attach(File file, String fileName) throws SendMailException {
        attachments.add(createAttachment(file, fileName));
        return this;
    }

    public EmailSender attachURL(URL url, String fileName) throws SendMailException {
        attachments.add(createURLAttachment(url, fileName));
        return this;
    }

    private MimeBodyPart createAttachment(File file, String fileName) throws SendMailException {
        MimeBodyPart   attachmentPart = new MimeBodyPart();
        FileDataSource fds            = new FileDataSource(file);
        try {
            attachmentPart.setDataHandler(new DataHandler(fds));
            attachmentPart.setFileName(null == fileName ? MimeUtility.encodeText(fds.getName()) : MimeUtility.encodeText(fileName));
        } catch (Exception e) {
            throw new SendMailException(e);
        }
        return attachmentPart;
    }

    private MimeBodyPart createURLAttachment(URL url, String fileName) throws SendMailException {
        MimeBodyPart attachmentPart = new MimeBodyPart();

        DataHandler dataHandler = new DataHandler(url);
        try {
            attachmentPart.setDataHandler(dataHandler);
            attachmentPart.setFileName(null == fileName ? MimeUtility.encodeText(fileName) : MimeUtility.encodeText(fileName));
        } catch (Exception e) {
            throw new SendMailException(e);
        }
        return attachmentPart;
    }

    public void send() throws SendMailException {
        if (text == null && html == null) {
            throw new IllegalArgumentException("At least one context has to be provided: Text or Html");
        }

        MimeMultipart cover;
        boolean       usingAlternative = false;
        boolean       hasAttachments   = attachments.size() > 0;

        try {
            if (text != null && html == null) {
                // TEXT ONLY
                cover = new MimeMultipart("mixed");
                cover.addBodyPart(textPart());
            } else {
                // HTML ONLY
                cover = new MimeMultipart("mixed");
                cover.addBodyPart(htmlPart());
            }

            MimeMultipart content = cover;

            for (MimeBodyPart attachment : attachments) {
                content.addBodyPart(attachment);
            }

            msg.setContent(content);
            msg.setSentDate(new Date());
            Transport.send(msg);
        } catch (Exception e) {
            throw new SendMailException(e);
        }
    }

    private MimeBodyPart textPart() throws MessagingException {
        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setText(text);
        return bodyPart;
    }

    private MimeBodyPart htmlPart() throws MessagingException {
        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent(html, "text/html; charset=utf-8");
        return bodyPart;
    }

}
