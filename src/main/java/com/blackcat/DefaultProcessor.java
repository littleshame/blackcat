package com.blackcat;


import com.blackcat.api.HttpRequest;
import com.blackcat.api.HttpResponse;
import com.blackcat.exception.ServletException;
import com.blackcat.servlet.DefaultServlet;
import com.blackcat.servlet.Servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

/**
 * @author: tjc
 * @Date: 2019-7-12
 */
public class DefaultProcessor {

    DefaultConnector connector = null;

    public DefaultProcessor(DefaultConnector connector) {
        this.connector = connector;
    }

    public void process(Socket socket) {
        InputStream input = null;
        OutputStream output = null;
        try {
            input = socket.getInputStream();
            output = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpRequest request = new HttpRequest(input);
        request.setContext(connector.getContext());
        request.parse();
        HttpResponse response = new HttpResponse(output);
        response.setRequest(request);

        Servlet servlet = null;
        try {
            servlet = map(request);
            //实例化可以做个缓存
            if(servlet == null) {
                servlet = new DefaultServlet();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            servlet.service(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        }


        try {
            input.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 路由映射对应servlet
     *
     * @param request
     * @return
     */
    private Servlet map(HttpRequest request) throws ClassNotFoundException {
        return getContext().servletMapping.get(request.getUrl());


    }

    private StandardContext getContext() {
        return this.connector.getContext();
    }
}
