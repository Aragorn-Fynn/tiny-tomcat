package com.feng.server.core;

import com.feng.server.connector.http.HttpRequest;
import com.feng.server.connector.http.HttpResponse;

import java.io.IOException;

/**
 * Processor interface is used to process the request and response.
 */
public interface Processor {

    void process(HttpRequest request, HttpResponse response) throws IOException;
}
