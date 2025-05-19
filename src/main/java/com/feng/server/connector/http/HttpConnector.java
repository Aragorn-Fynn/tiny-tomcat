package com.feng.server.connector.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpConnector implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpConnector.class);
    private boolean shutdown=false;

    @Override
    public void run() {
        ServerSocket ss=null;
        try {
            ss = new ServerSocket(8080, 1, InetAddress.getByName("127.0.0.1"));
            LOGGER.info("Start server successfully!");
        } catch (IOException e) {
            LOGGER.error("Start server error!", e);
            System.exit(1);
        }

        // Loop waiting for a request
        while (!shutdown) {
            Socket socket;
            try {
                LOGGER.info("Start to accept a new request!");
                socket = ss.accept();

                HttpProcessor processor = new HttpProcessor(this);
                processor.process(socket);

                LOGGER.info("Handle request successfully!");
            } catch (IOException e) {
                LOGGER.error("Handle http request error!", e);
            }
        }
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }
}
