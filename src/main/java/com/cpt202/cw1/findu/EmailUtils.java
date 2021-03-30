package com.cpt202.cw1.findu;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class EmailUtils {

    private static String host = "smtp.163.com";
    private static String username = "ToFINDYou@163.com";
    private static String password = "QCBETLSMZQGJZAWS";
    private static String protocol = "smtp";
    private static boolean debug = true;

    private EmailUtils() {
    }

    public static void sendEmail(String to, String code) throws MessagingException, UnsupportedEncodingException {
        //连接邮件服务器的参数设置
        Properties properties = new Properties();
        //设置服务器地址
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", "994");
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        //设置用户认证方式
        properties.setProperty("mail.smtp.auth", "true");
        //设置传输协议
        properties.setProperty("mail.transport.protocol", protocol);
        //创建连接对象，连接到邮箱服务器
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        session.setDebug(debug);
        //创建邮件对象
        Message message = new MimeMessage(session);
        //设置发件人
        message.setFrom(new InternetAddress(username));
        //设置收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setRecipient(Message.RecipientType.CC, new InternetAddress(username));
        //设置邮件主题
        message.setSubject("Verification code mail");
        //设置图片节点
        //MimeBodyPart mimeBodyPart = new MimeBodyPart();
        ////mimeBodyPart.setContent("请注意您的信息","text/html;charset=UTF-8");
        //DataHandler dataHandler = new DataHandler(new FileDataSource("G:\\360downloads\\310684.jpg"));
        //mimeBodyPart.setDataHandler(dataHandler);
        //设置图片id以便在html中调用
        //mimeBodyPart.setContentID("imgt");
        //设置文字节点
        //MimeBodyPart text = new MimeBodyPart();
        //text.setContent("<a href='http://webjob.free.ngrok.cc/user/jihuo?code=" + code + "'>点击此链接激活您的账号</a><img src='cid:imgt'>","text/html;charset=UTF-8");
        //设置附件
        //MimeBodyPart fujian = new MimeBodyPart();
        //DataHandler f = new DataHandler(new FileDataSource("G:\\360downloads\\303167.jpg"));
        //fujian.setDataHandler(f);
        //fujian.setFileName(MimeUtility.encodeText(f.getName()));
        //MimeMultipart mimeMultipart = new MimeMultipart();
        //加入所有节点
        //mimeMultipart.addBodyPart(mimeBodyPart);
        ///mimeMultipart.addBodyPart(text);
        //mimeMultipart.addBodyPart(fujian);
        //mimeMultipart.setSubType("mixed");
        //message.setContent(mimeMultipart);
        //设置邮件正文
        message.setContent("<p style='font-size: 34px'>Please check your verification code. It will be valid in ten minutes"+"\n"+code+"</p>", "text/html;charset=UTF-8");
        Transport transport = session.getTransport("smtp");
        transport.connect();
        //发送邮件
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
}