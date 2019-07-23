package com.blackcat;

import bean.HelloServlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
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

        Class<?> clazz = null;
        Servlet servlet = null;
        try {
            clazz = map(request);
            //实例化可以做个缓存
            if(clazz == null) {
                servlet = new DefaultServlet();
            }else{
                servlet = (Servlet) clazz.newInstance();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        servlet.service(request, response);


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
    private Class<?> map(HttpRequest request) throws ClassNotFoundException {
        Map<String, String> requestMappings = getContext().requestMappings;
        String url = request.getUrl();
        String className = requestMappings.get(url);
        if(className == null) {
            return null;
        }
        return Class.forName(className);
    }

    private StandardContext getContext() {
        return this.connector.getContext();
    }
}
