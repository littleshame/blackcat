package com.blackcat;

/**
 * @author: tjc
 * @Date: 2019-7-12
 */
public abstract class Servlet {
    public void service(HttpRequest request, HttpResponse response){
        if(request.getMethod().equals(RequestMethod.GET)) {
            doGet(request, response);
        }else if(request.getMethod().equals(RequestMethod.POST)) {
            doPost(request, response);
        }
    }

    public abstract void doGet(HttpRequest request, HttpResponse response);
    public abstract void doPost(HttpRequest request, HttpResponse response);

}
