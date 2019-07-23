package com.blackcat;

import com.blackcat.api.Context;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/**
 * @author: tjc
 * @Date: 2019-7-13
 */
public class StandardContext implements Context{

    /**
     * classLoader只允许读取webroot下的class文件
     */
    private String repositoryDir = "D:/IntelliJ IDEA 2017.1.6/workspace/blackcat/WEB-INF/classes";

    private String webroot = "D:/IntelliJ IDEA 2017.1.6/workspace/blackcat/WEB-INF";

    /**
     * 已加载的servlet类池
     * key为路由，val为servlet类全限定名
     */
    Map<String, String> requestMappings = new HashMap(16);

    /**
     * servlet类加载器
     */
    ServletLoader servletLoader = null;

    /**
     * http连接器
     */
    DefaultConnector connector = null;


    public void start() {
        onServlet();
    }

    private void onServlet() {
        try {
            try {
                loadRepository(repositoryDir);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载仓库路径下的全部有效servlet类
     * @param dir
     * @throws MalformedURLException
     * @throws ServletException
     */
    private void loadRepository(String dir) throws MalformedURLException, ServletException, ClassNotFoundException {
        //递归访问classes存放目录，分析class文件
        //分析class是否有@WebServlet注解，加载@WebServlet注解的class类到requestMappings
        String urlPath = "file:"+ dir;
        URL[] repositorys = new URL[]{new URL(urlPath)};
        URLClassLoader urlClassLoader = new URLClassLoader(repositorys);
        for(URL repository : repositorys) {
            //递归获取所有className
            Set<String> classes = loadClassName("");
            //加载servlet，解析路由
            for(String className : classes) {
                Class<?> clazz = urlClassLoader.loadClass(className);
                if(!clazz.isAnnotationPresent(WebServlet.class)){
                    continue;
                }
                WebServlet webServlet = clazz.getAnnotation(WebServlet.class);
                requestMappings.put(webServlet.value(),className);
            }
        }
        System.out.println();
    }

    public Set<String> loadClassName(String pkg) {
        Set classes = new HashSet();
        String path = repositoryDir + "/" + pkg.replaceAll("\\.","/");
        File parentDir = new File(path);
        File[] sonfiles = parentDir.listFiles();
        for (File son : sonfiles) {
            if (son.isDirectory()) {
                classes.addAll(loadClassName(pkg + son.getName() + "."));
            }else {
                if(son.getName().lastIndexOf(".class")>0){
                    classes.add(pkg + son.getName().substring(0,son.getName().lastIndexOf('.')));
                }
            }
        }
        return classes;
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
