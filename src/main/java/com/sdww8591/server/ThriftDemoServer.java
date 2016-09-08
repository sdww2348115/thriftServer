package com.sdww8591.server;

import com.sdww8591.service.Demo;
import com.sdww8591.serviceImp.DemoServiceImp;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sdww on 16-9-7.
 */
public class ThriftDemoServer {

    private static final Logger logger = LoggerFactory.getLogger(ThriftDemoServer.class);

    public void start() {
        final Demo.Processor processor = new Demo.Processor(new DemoServiceImp());

        final Runnable demoServer = new Runnable() {
            @Override
            public void run() {
                simple(processor);
            }
        };
        new Thread(demoServer, "thrift demo server").start();
    }


    /**
     * 启动一个simple server
     * @param processor
     */
    private void simple(Demo.Processor processor) {
        try {
            TServerTransport serverTransport = new TServerSocket(9001);
            TServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(processor));
            logger.info("Starting the simple server...");
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
