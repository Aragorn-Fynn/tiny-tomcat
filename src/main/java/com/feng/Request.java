package com.feng;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

/**
 * A class represents http request
 */
public class Request {

    private InputStream input;
    private List<String> requestLines;
    private String uri;

    public Request(InputStream input) {
        this.input=input;
    }

    public String getUri() {
        return uri;
    }

    public void parse() throws IOException {
        requestLines = IOUtils.readLines(input, Charsets.UTF_8);
        this.uri = parseUri();
    }

    // parse uri from request inputstream
    private String parseUri() {

        String headerLine = requestLines.get(0);
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
