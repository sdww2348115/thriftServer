package com.sdww8591.serviceImp;

import com.sdww8591.service.Demo;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sdww on 16-9-7.
 */
public class DemoServiceImp implements Demo.Iface{

    private static final Logger logger = LoggerFactory.getLogger(DemoServiceImp.class);

    @Override
    public String echo(String str) throws TException {
        logger.info("process request:" + str);
        System.out.println("process request:" + str);
        return str;
    }
}
