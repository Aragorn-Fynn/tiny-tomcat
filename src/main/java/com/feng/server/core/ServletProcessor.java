package com.feng.server.core;

import com.feng.server.connector.http.HttpRequest;
import com.feng.server.connector.http.HttpResponse;
import com.feng.server.connector.http.RequestFacade;
import com.feng.server.connector.http.ResponseFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

import static com.feng.server.constant.Constants.WEB_ROOT;

/**
 * ServletProcessor is used to process servlet request.
 * It will load the servlet class and call its service method.
 * It will also create a request and response object and pass them to the servlet.
 */
public class ServletProcessor implements Processor {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServletProcessor.class);

    @Override
    public void process(HttpRequest request, HttpResponse response) {
        String uri = request.getRequestURI();
        String servletName = uri.substring(uri.lastIndexOf("/")+1);
        URLClassLoader classLoader = null;
        URL[] urls = new URL[1];
        URLStreamHandler streamHandler = null;
        String repository = null;
        try {
            File classPath = new File(WEB_ROOT);
            repository = new URL("file", null, classPath.getCanonicalPath() + File.separator).toString();
            urls[0] = new URL(null, repository, streamHandler);
            classLoader = new URLClassLoader(urls);
        } catch (IOException e) {
            LOGGER.error("init class loader error!", e);
        }

        Class myClass = null;
        try {
            assert classLoader != null;
            myClass = classLoader.loadClass(servletName);
        } catch (ClassNotFoundException e) {
            LOGGER.error("load class error!", e);
        }

        Servlet servlet;
        try {
            RequestFacade requestFacade = new RequestFacade(request);
            ResponseFacade responseFacade = new ResponseFacade(response);
            servlet = (Servlet) myClass.newInstance();
            servlet.service(requestFacade, responseFacade);
        } catch (Exception e) {
            LOGGER.error("servlet runs error!", e);
        }
    }
}
