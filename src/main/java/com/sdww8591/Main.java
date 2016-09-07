package com.sdww8591;

import com.sdww8591.server.ThriftDemoServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main( String[] args ) {
        logger.info("start....");
        new ThriftDemoServer().start();
    }
}
