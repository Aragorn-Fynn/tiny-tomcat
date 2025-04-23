package com.feng;

import java.io.IOException;
import java.io.InputStream;

/**
 * A class represents http request
 */
public class Request {

    private InputStream input;
    private String uri;

    public Request(InputStream input) {
        this.input=input;
    }

    public String getUri() {
        return uri;
    }

    public void parse() throws IOException {
        byte[] bytes = new byte[2048];
        input.read(bytes);
        char[] arr=new char[2048];
        for (int i=0; i<arr.length; i++) {
            arr[i]= (char) bytes[i];
        }
        String requestLine = new String(arr);
        this.uri = parseUri(requestLine);
    }

    // parse uri from request inputstream
    private String parseUri(String headerLine) {
        int firstBlank = headerLine.indexOf(' ');
        if (firstBlank!=-1) {
            int secondBlank = headerLine.indexOf(' ', firstBlank+1);
            if (secondBlank!=-1) {
                return headerLine.substring(firstBlank+1, secondBlank);
            }
        }
        return null;
    }
}
