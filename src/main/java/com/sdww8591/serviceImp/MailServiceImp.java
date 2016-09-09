package com.sdww8591.serviceImp;

import com.sdww8591.service.mail.Mail;
import com.sdww8591.service.mail.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

/**
 * Created by sdww on 16-9-8.
 */
public class MailServiceImp implements MailService.Iface{

    private final static Logger logger = LoggerFactory.getLogger(MailServiceImp.class);

    // 邮件发送协议
    private final static String PROTOCOL = "smtp";

    // SMTP邮件服务器
    private final static String HOST = "smtp.163.com";

    // SMTP邮件服务器默认端口
    private final static String PORT = "25";

    // 是否要求身份认证
    private final static String IS_AUTH = "true";

    // 是否启用调试模式（启用调试模式可打印客户端与服务器交互过程时一问一答的响应消息）
    private final static String IS_ENABLED_DEBUG_MOD = "true";

    // 发件人
    private static String from = "sdww8591@163.com";

    // 收件人
    private static String to = "wx2348115@sina.com";

    // 初始化连接邮件服务器的会话信息
    private static Properties props = null;

    static {
        props = new Properties();
        props.setProperty("mail.transport.protocol", PROTOCOL);
        props.setProperty("mail.smtp.host", HOST);
        props.setProperty("mail.smtp.port", PORT);
        props.setProperty("mail.smtp.auth", IS_AUTH);
        props.setProperty("mail.debug",IS_ENABLED_DEBUG_MOD);
    }

    public static void main(String[] args) throws Exception {
        // 发送文本邮件
        //new MailServiceImp().sendTextEmail("test");
        Mail mail = new Mail();
        mail.setTitle("附件测试");
        mail.setText("hello world");
        mail.setRecipient("wx2348115@sina.com");
        new MailServiceImp().sendEmail(mail);
    }

    /**
     * 发送简单的文本邮件
     */
    public void sendTextEmail(Mail mail) throws Exception {
        // 创建Session实例对象
        Session session = Session.getDefaultInstance(props);

        // 创建MimeMessage实例对象
        MimeMessage message = new MimeMessage(session);
        // 设置发件人
        message.setFrom(new InternetAddress(from));
        // 设置邮件主题
        message.setSubject(mail.getTitle());
        // 设置收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(mail.getRecipient()));
        // 设置发送时间
        message.setSentDate(new Date());
        // 设置纯文本内容为邮件正文
        message.setText(mail.getText());
        // 保存并生成最终的邮件内容
        message.saveChanges();

        // 获得Transport实例对象
        Transport transport = session.getTransport();
        // 打开连接
        transport.connect("sdww8591@163.com", "sdww2348115");
        // 将message对象传递给transport对象，将邮件发送出去
        transport.sendMessage(message, message.getAllRecipients());
        // 关闭连接
        transport.close();
    }

    public void sendEmail(Mail mail) throws Exception {
        Session session = Session.getDefaultInstance(props);
        MimeMessage message = getMimeMessage(mail, session);
        Transport transport = session.getTransport();
        transport.connect("sdww8591@163.com", "sdww2348115");
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    public MimeMessage getMimeMessage(Mail mail, Session session) throws Exception{

        // 创建MimeMessage实例对象
        MimeMessage mimeMessage = new MimeMessage(session);

        // 设置发件人
        mimeMessage.setFrom(new InternetAddress(from));

        // 设置邮件主题
        mimeMessage.setSubject(mail.getTitle());

        // 设置收件人
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(mail.getRecipient()));

        // 设置发送时间
        mimeMessage.setSentDate(new Date());

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setText(mail.getText());

        MimeBodyPart attach = new MimeBodyPart();
        DataHandler dh = new DataHandler(new FileDataSource("/home/sdww/SwitchyOmega.crx"));
        attach.setDataHandler(dh);
        attach.setFileName(dh.getName());

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
        multipart.addBodyPart(attach);

        mimeMessage.setContent(multipart);
        mimeMessage.saveChanges();

        return mimeMessage;
    }

    @Override
    public boolean smtpMail(Mail mail) {
        try {
            sendTextEmail(mail);
            return true;
        } catch (Exception e) {
            logger.error(e.getStackTrace().toString());
            return false;
        }
    }
}
