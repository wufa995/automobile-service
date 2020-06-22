package cn.wufa995.common.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * 邮箱工具类
 * @author: wufa995[wufa995@qq.com]
 * @date: 2018年11月12日 11时01分
 */

public class SendEmailHelper {

    public static String PROXY_MAIL;
    public static String PASSWORD;
    /**
     * 发送邮件
     * @param email
     * @param content
     * @throws Exception
     */
    public static void email(String email,String content) throws Exception {
        Properties properties = new Properties();
        //发送邮件协议
        properties.setProperty("mail.transport.protocol", "smtp");
        //需要验证
        properties.setProperty("mail.smtp.auth", "true");
        Session session = Session.getInstance(properties);
        //debug模式
        session.setDebug(true);
        //邮件信息
        Message messgae = new MimeMessage(session);
        //设置发送人
        messgae.setFrom(new InternetAddress(PROXY_MAIL));
        //设置邮件内容
        messgae.setContent(content,"text/html;charset=UTF-8");
        //设置邮件主题
        messgae.setSubject("汽车维修预约-通知");
        //发送邮件
        Transport tran = session.getTransport();
        tran.connect("smtp.qq.com",25, PROXY_MAIL, PASSWORD);
        //设置邮件接收人
        tran.sendMessage(messgae, new Address[]{ new InternetAddress(email)});
        tran.close();
    }
}