package com.blackcat;

import com.blackcat.api.Context;
import com.blackcat.exception.ServletException;
import com.blackcat.servlet.Servlet;
import com.blackcat.servlet.WebServlet;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/**
 * @author: tjc
 * @Date: 2019-7-13
 */
public class StandardContext implements Context {

    /**
     * 网站根目录
     */
    private String webroot = "D:/IntelliJ IDEA 2017.1.6/workspace/blackcat/WEB-INF";

    /**
     * 已加载的servlet类池
     * key为路由，val为servlet类全限定名
     */
    Map<String, String> requestMappings = null;

    Map<String, Servlet> servletMapping = null;

    /**
     * servlet类加载器
     */
    ServletLoader servletLoader = null;

    /**
     * http连接器
     */
    DefaultConnector connector = null;


    public void start() {
        servletLoader = new ServletLoader();
        servletMapping = servletLoader.registerServlet(webroot);
    }




    /**
     * 开发中使用方法
     *
     * @param requestMappings
     */
    public void setRequestMappings(HashMap<String, String> requestMappings) {
        this.requestMappings = requestMappings;
    }

    @Override
    public String getWebRoot() {
        return webroot;
    }


}
