package com.feng.server.startup;

import com.feng.server.connector.http.HttpConnector;

/**
 * Bootstrap is the entry point of the server.
 * It will create a connector and start it.
 */
public class Bootstrap {
    public static void main(String[] args) {
        HttpConnector connector = new HttpConnector();
        connector.start();
    }
}
