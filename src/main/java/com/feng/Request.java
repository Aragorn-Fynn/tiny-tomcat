package com.feng;

import sun.misc.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * A class represents http request
 */
public class Request {

    private InputStream input;
    private byte[] bytes;
    private String uri;

    public Request(InputStream input) {
        this.input=input;
    }

    public String getUri() {
        return uri;
    }

    public void parse() throws IOException {
        bytes = IOUtils.readFully(input, -1, true);
        this.uri = parseUri();
    }

    // parse uri from request inputstream
    private String parseUri() {
        StringBuilder buffer = new StringBuilder();
        for (byte aByte : bytes)
            buffer.append((char) aByte);

        String request = buffer.toString();

        int firstBlank = request.indexOf(' ');
        if (firstBlank!=-1) {
            int secondBlank = request.indexOf(' ', firstBlank+1);
            if (secondBlank!=-1) {
                return request.substring(firstBlank+1, secondBlank);
            }
        }
        return null;
    }
}
