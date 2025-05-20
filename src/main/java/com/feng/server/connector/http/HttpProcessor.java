package com.feng.server.connector.http;

import com.feng.server.core.Processor;
import com.feng.server.core.ServletProcessor;
import com.feng.server.core.StaticResourceProcessor;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * HttpProcessor is used to process a request.
 */
public class HttpProcessor {
    HttpConnector connector;
    public HttpProcessor(HttpConnector connector) {
        this.connector = connector;
    }

    /**
     * Process the request:
     * 1. create a request
     * 2. create a response
     * 3. handle the request
     * 4. close the socket
     * @param socket
     */
    public void process(Socket socket) throws IOException {
        SocketInputStream input;
        OutputStream output;
        try {
            input = new SocketInputStream(socket.getInputStream());
            output = socket.getOutputStream();

            // create a http request
            HttpRequest request = new HttpRequest(input);
            // create a http response
            HttpResponse response = new HttpResponse(output);
            response.setRequest(request);

            // handle the http request
            handle(request, response);
        } finally {
            socket.close();
        }
    }

    private void handle(HttpRequest request, HttpResponse response) throws IOException {
        Processor processor;
        if (request.getRequestURI().startsWith("/servlet/")) {
            processor = new ServletProcessor();
        } else {
            processor = new StaticResourceProcessor();
        }
        processor.process(request, response);
    }

}
