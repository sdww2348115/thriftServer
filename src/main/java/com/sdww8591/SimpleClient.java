package com.sdww8591;

import com.sdww8591.service.Demo;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * Created by sdww on 16-9-7.
 */
public class SimpleClient {

  public void connect(String str) {
    try {
      // 设置调用的服务地址为本地，端口为 7911
      TTransport transport = new TSocket("sdww8591.oicp.net", 9002);
      transport.open();
      // 设置传输协议为 TBinaryProtocol
      TProtocol protocol = new TBinaryProtocol(transport);
      Demo.Client client = new Demo.Client(protocol);
      // 调用服务的 helloVoid 方法
      String result = client.echo(str);
      System.out.println(result);
      transport.close();
    } catch (TTransportException e) {
      e.printStackTrace();
    } catch (TException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    new SimpleClient().connect("ni hao a");
  }
}
