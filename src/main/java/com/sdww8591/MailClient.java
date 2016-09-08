package com.sdww8591;

import com.sdww8591.service.mail.Mail;
import com.sdww8591.service.mail.MailService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * Created by sdww on 16-9-8.
 */
public class MailClient {

    public void connect() {
        try {
            // 设置调用的服务地址为本地，端口为 7911
            TTransport transport = new TSocket("sdww8591.oicp.net", 9001);
            transport.open();
            // 设置传输协议为 TBinaryProtocol
            TProtocol protocol = new TBinaryProtocol(transport);
            MailService.Client client = new MailService.Client(protocol);
            // 调用服务的 发送简单邮件 方法
            Mail mail = new Mail();
            mail.setTitle("first thrift test");
            mail.setText("hello world");
            mail.setRecipient("2324717228@qq.com");
            client.smtpMail(mail);
            transport.close();
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MailClient().connect();
    }
}
