package com.feng.server.connector;

import com.feng.server.connector.http.HttpRequest;
import com.feng.server.connector.http.HttpResponse;

import java.io.IOException;

public interface Processor {

    void process(HttpRequest request, HttpResponse response) throws IOException;
}
