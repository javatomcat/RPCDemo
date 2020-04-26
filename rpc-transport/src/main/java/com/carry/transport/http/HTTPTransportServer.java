package com.carry.transport.http;

import com.carry.transport.RequestHandler;
import com.carry.transport.TransportServer;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
public class HTTPTransportServer implements TransportServer {
    private RequestHandler requestHandler;

    private Server server;

    @Override
    public void init(int port, RequestHandler handler) {
        this.requestHandler = handler;
        this.server = new Server(port);

        //Serverlet接受请求
        ServletContextHandler servletContextHandler = new ServletContextHandler();
        server.setHandler(servletContextHandler);

        ServletHolder holder = new ServletHolder(new RequestServlet());
        servletContextHandler.addServlet(holder, "/*");
    }

    @Override
    public void start() {
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void receive() {

    }

    @Override
    public void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    class RequestServlet extends HttpServlet {
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            InputStream in = req.getInputStream();
            OutputStream out = resp.getOutputStream();
            if (requestHandler != null) {
                requestHandler.doRequest(in, out);
            }
        }
    }
}
