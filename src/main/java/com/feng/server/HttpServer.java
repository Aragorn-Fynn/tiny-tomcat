package com.feng.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import static com.feng.server.constant.Constants.SHUTDOWN_COMMAND;

/**
 * A server that handles http request with ServerSocket/Socket.
 */
public class HttpServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);
    private boolean shutdown=false;

    // create a server socket and wait for connection from client
    public void await() {
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
        if (request.getUri().startsWith("/servlet/")) {
            Processor processor = new ServletProcessor();
            processor.process(request, response);
        } else {
            Processor processor = new StaticResourceProcessor();
            processor.process(request, response);
        }
    }

    public static void main(String[] args) {
        HttpServer server = new HttpServer();
        server.await();
    }
}
