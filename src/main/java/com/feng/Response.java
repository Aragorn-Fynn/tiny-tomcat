package com.feng;

import java.io.OutputStream;

/**
 * A class represents HTTP response
 */
public class Response {

    OutputStream output;

    public Response(OutputStream output) {
        this.output = output;
    }
}
