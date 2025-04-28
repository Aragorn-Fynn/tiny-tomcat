import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

public class PrimitiveServlet implements Servlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(PrimitiveServlet.class);
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        LOGGER.info("init");
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        LOGGER.info("from service");
        PrintWriter out = servletResponse.getWriter();
        out.println("HTTP/1.1 200 OK\r\n" +
                "Server: Microsoft-IIS/4.0\r\n" +
                "Date: Mon, 5 Jan 2004 13:13:33 GMT\r\n" +
                "Content-Type: text/html\r\n");
        out.println("Hello, Servlet!");
    }

    @Override
    public String getServletInfo() {
        return "";
    }

    @Override
    public void destroy() {
        LOGGER.info("destroy");
    }
}
