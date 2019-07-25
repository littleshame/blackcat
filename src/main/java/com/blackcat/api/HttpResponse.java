package com.blackcat.api;

import lombok.Getter;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author: tjc
 * @Date: 2019-7-9
 */
public class HttpResponse {
    @Getter
    private HttpRequest request;
    @Getter
    private OutputStream output;

    private String body;

    public HttpResponse(OutputStream output) {
        this.output = output;
    }

    public void setRequest(HttpRequest request) {
        this.request = request;
    }

    public void write(String body) {
        this.body = body;
    }

    public void writeOver() {
        StringBuffer sb = new StringBuffer();

        sb.append("HTTP/1.1 200 OK" + "\n");
        sb.append("Content-type:text/html;" + "\n");
        sb.append("\r\n\r\n");
        sb.append(body);
        try {
            output.write(sb.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
