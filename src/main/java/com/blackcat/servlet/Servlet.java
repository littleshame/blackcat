package com.blackcat.servlet;

import com.blackcat.api.HttpRequest;
import com.blackcat.api.HttpResponse;
import com.blackcat.RequestMethod;
import com.blackcat.exception.ServletException;

/**
 * @author: tjc
 * @Date: 2019-7-12
 */
public abstract class Servlet {
    public void service(HttpRequest request, HttpResponse response) throws ServletException {
        if(request.getMethod().equals(RequestMethod.GET)) {
            doGet(request, response);
        }else if(request.getMethod().equals(RequestMethod.POST)) {
            doPost(request, response);
        }
    }

    public abstract void doGet(HttpRequest request, HttpResponse response) throws ServletException;
    public abstract void doPost(HttpRequest request, HttpResponse response) throws ServletException;

}
