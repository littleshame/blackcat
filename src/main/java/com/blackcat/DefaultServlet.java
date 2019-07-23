package com.blackcat;

import com.blackcat.api.Context;

import javax.servlet.*;
import java.io.*;

/**
 * 默认servlet处理器，用于处理静态资源
 *
 * @author: tjc
 * @Date: 2019-7-9
 */
public class DefaultServlet extends Servlet {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        Context context = request.getContext();
        String webRoot = context.getWebRoot();
        String resourcePath = webRoot + request.getUrl();
        File resource = new File(resourcePath);
        StringBuffer sb = new StringBuffer();
        OutputStream out = response.getOutput();
        String type = request.getUrl().substring(request.getUrl().indexOf("."), request.getUrl().length());

        sb.append("HTTP/1.1 200 OK" + "\n");
        sb.append("Content-type:text/html;" + "\n");
        sb.append("\r\n\r\n");
        if (".html".equals(type)) {
            try {
                String s = null;
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(resource)));
                while ((s=reader.readLine()) != null) {
                    sb.append(s);
                }
                out.write(sb.toString().getBytes());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {

    }

}
