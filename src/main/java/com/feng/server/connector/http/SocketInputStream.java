package com.feng.server.connector.http;

import javax.servlet.ServletInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * SocketInputStream is a wrapper for InputStream
 * It is convenient to parse the request
 */
public class SocketInputStream extends ServletInputStream {

    private final InputStream inputStream;

    public SocketInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public int read() throws IOException {
        return inputStream.read();
    }
}
