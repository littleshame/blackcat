package com.blackcat.servlet;

import com.blackcat.exception.ServletException;
import com.blackcat.api.Context;
import com.blackcat.api.HttpRequest;
import com.blackcat.api.HttpResponse;

import java.io.*;

/**
 * 默认servlet处理器，用于处理静态资源
 *
 * @author: tjc
 * @Date: 2019-7-9
 */
public class DefaultServlet extends com.blackcat.servlet.Servlet {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws ServletException {
        Context context = request.getContext();
        String resourcePath = context.getWebRoot() + request.getUrl();
        File resource = new File(resourcePath);
        String type = request.getUrl().substring(request.getUrl().indexOf("."), request.getUrl().length());

        StringBuffer sb = new StringBuffer();
        if (resource.exists()) {
            String s = null;
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(resource)));
                while ((s = reader.readLine()) != null) {
                    sb.append(s);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            response.write(sb.toString());


            if (".html".equals(type)) {
                response.writeOver();
            }


        } else {
            throw new ServletException("404 找不到指定页面");
        }
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {

    }

}
