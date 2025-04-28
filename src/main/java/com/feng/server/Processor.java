package com.feng.server;

import java.io.IOException;

public interface Processor {

    void process(Request request, Response response) throws IOException;
}
