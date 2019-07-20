package com.blackcat;

import lombok.Getter;

import java.io.OutputStream;

/**
 * @author: tjc
 * @Date: 2019-7-9
 */
public class HttpResponse {
    @Getter
    private HttpRequest request;

    private OutputStream output;

    public HttpResponse(OutputStream output) {
        this.output = output;
    }

    public void setRequest(HttpRequest request) {
        this.request = request;
    }
}
