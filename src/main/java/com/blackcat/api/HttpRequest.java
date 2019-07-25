package com.blackcat.api;

import com.blackcat.StandardContext;
import com.blackcat.api.Context;
import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author: tjc
 * @Date: 2019-7-9
 */
public class HttpRequest {
    @Getter
    private String url;
    @Getter
    private String method;
    @Getter
    private String protocol;

    private Context context;

    private InputStream input;

    public HttpRequest(InputStream input) {
        this.input = input;
    }

    public void parse() {
        StringBuffer request = new StringBuffer(2048);
        byte[] buf = new byte[2048];
        int i = 0;
        try {
            i = input.read(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int j = 0; j < i; j++) {
            request.append((char) buf[j]);
        }
        //System.out.println(request.toString());

        //解析请求行
        String line = request.substring(0, request.indexOf("\n"));
        int index1 = line.indexOf(' ');
        int index2 = line.indexOf(' ', index1 + 1);
        method = line.substring(0, index1);
        url = line.substring(index1 + 1, index2);
        protocol = line.substring(index2, line.length() - 1);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(StandardContext context) {
        this.context = context;
    }
}
