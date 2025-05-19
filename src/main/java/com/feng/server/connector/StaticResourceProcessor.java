package com.feng.server.connector;

import com.feng.server.connector.http.HttpRequest;
import com.feng.server.connector.http.HttpResponse;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import static com.feng.server.constant.Constants.WEB_ROOT;

public class StaticResourceProcessor implements Processor {
    private static final Logger LOGGER = LoggerFactory.getLogger(StaticResourceProcessor.class);
    @Override
    public void process(HttpRequest request, HttpResponse response) throws IOException {

        sendStaticResource(request, response);
    }

    public void sendStaticResource(HttpRequest request, HttpResponse response) {
        try {
            OutputStream output = response.getOutputStream();
            File file = new File(WEB_ROOT, request.getRequestURI());
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
