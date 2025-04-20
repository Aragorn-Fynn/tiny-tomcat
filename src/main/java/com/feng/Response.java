package com.feng;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * A class represents HTTP response
 */
public class Response {

    private static final Logger LOGGER = LoggerFactory.getLogger(Response.class);

    Request request;
    OutputStream output;

    public Response(OutputStream output) {
        this.output = output;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void sendStaticResource() {
        try {
            File file = new File(HttpServer.WEB_ROOT, request.getUri());
            if (file.exists()) {
                IOUtils.write(FileUtils.readFileToByteArray(file), output);
            } else {
                String errorMessage = "HTTP/1.1 404 File Not Found\r\n"
                        +"Content-Type: text/html\r\n"
                        +"Content-Length: 23\r\n"
                        +"\r\n"
                        +"<h1>File Not Found</h1>";
                output.write(errorMessage.getBytes());
            }
        } catch (IOException e) {
            LOGGER.error("Send static resource error!", e);
        }
    }
}
