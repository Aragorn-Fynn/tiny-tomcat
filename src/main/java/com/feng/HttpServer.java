package com.feng;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A server that handles http request with ServerSocket/Socket.
 */
public class HttpServer {
    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "WEB_ROOT";
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);
    private static final String SHUTDOWN_COMMAND = "SHUTDOWN";
    private boolean shutdown=false;

    // create a server socket and wait for connection from client
    public void await() {
        ServerSocket ss=null;
        try {
            ss = new ServerSocket(8080, 1, InetAddress.getByName("127.0.0.1"));
            LOGGER.info("Create server socket successfully!");
        } catch (IOException e) {
            LOGGER.error("Create server socket error!", e);
            System.exit(1);
        }

        // Loop waiting for a request
        while (!shutdown) {
            Socket socket;
            InputStream input;
            OutputStream output;
            try {
                LOGGER.info("Start to accept a new request!");
                socket = ss.accept();

                // create a request
                input = socket.getInputStream();
                Request request = new Request(input);

                // create a response
                output = socket.getOutputStream();
                Response response = new Response(output);
                response.setRequest(request);

                // handle the request
                handle(request, response);

                // close the socket
                socket.close();

                shutdown = SHUTDOWN_COMMAND.equals(request.getUri());
                LOGGER.info("Handle request successfully!");
            } catch (IOException e) {
                LOGGER.error("Handle http request error!", e);
            }
        }
    }

    private void handle(Request request, Response response) throws IOException {
        request.parse();
        response.sendStaticResource();
    }

    public static void main(String[] args) {
        HttpServer server = new HttpServer();
        server.await();
    }
}
