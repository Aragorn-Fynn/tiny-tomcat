package com.feng.server.connector.http;

import com.feng.server.connector.Processor;
import com.feng.server.connector.ServletProcessor;
import com.feng.server.connector.StaticResourceProcessor;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class HttpProcessor {
    HttpConnector connector;
    public HttpProcessor(HttpConnector connector) {
        this.connector = connector;
    }

    public void process(Socket socket) {
        SocketInputStream input;
        OutputStream output;
        try {
            input = new SocketInputStream(socket.getInputStream());
            output = socket.getOutputStream();

            // create a request
            HttpRequest request = new HttpRequest(input);
            // create a response
            HttpResponse response = new HttpResponse(output);
            response.setRequest(request);

            // handle the request
            handle(request, response);

            // close the socket
            socket.close();

        } catch (Exception e) {

        }
    }

    private void handle(HttpRequest request, HttpResponse response) throws IOException {
        if (request.getRequestURI().startsWith("/servlet/")) {
            Processor processor = new ServletProcessor();
            processor.process(request, response);
        } else {
            Processor processor = new StaticResourceProcessor();
            processor.process(request, response);
        }
    }

}
