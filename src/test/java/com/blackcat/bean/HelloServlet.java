package com.blackcat.bean;

import com.blackcat.api.HttpRequest;
import com.blackcat.api.HttpResponse;
import com.blackcat.servlet.Servlet;
import com.blackcat.servlet.WebServlet;

/**
 * @author: tjc
 * @Date: 2019-7-13
 */
@WebServlet("/hello")
public class HelloServlet extends Servlet {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        System.out.println("hello get");
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {

    }
}
