package com.blackcat;

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
public class ServletLoader {

    /**
     * 注册仓库路径下的全部有效servlet类（被@WebServlet注释的类）
     *
     * @param dir
     * @throws MalformedURLException
     * @throws ServletException
     */
    public Map<String, Servlet> registerServlet(String dir) {
        Map<String, Servlet> requestMappings = new HashMap<>();
        List<Class<Servlet>> servletList = loadClasses(dir);
        servletList.forEach(servlet -> {
            String url = HandlerAnnotation(servlet);
            if (url != null) {
                try {
                    requestMappings.put(url, servlet.newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        return requestMappings;
    }

    private String HandlerAnnotation(Class<Servlet> servlet) {
        WebServlet webServlet = servlet.getAnnotation(WebServlet.class);
        return webServlet.value();
    }

    public List<Class<Servlet>> loadClasses(String dir) {
        List<Class<Servlet>> servletClassList = new LinkedList<>();
        List<File> fileList = new LinkedList<>();
        try {
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{new URL("file://" + dir + "/classes/")});
            scanClassName(fileList, new File(dir + "/classes/"));
            fileList.forEach(file -> {
                try {
                    servletClassList.add((Class<Servlet>) urlClassLoader.
                            loadClass(file.getPath()
                                    .substring((dir + "/classes/").length(),
                                            file.getPath().length() - 6)
                                    .replaceAll("\\\\", ".")));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return servletClassList;
    }


    /**
     * 递归扫描目录下的class文件
     *
     * @return
     */
    public void scanClassName(List<File> fileList, File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            Arrays.stream(files).forEach(f -> {
                if (f.isFile() && validate(f.getName())) {
                    fileList.add(f);
                } else {
                    scanClassName(fileList, f);
                }
            });
        }
    }

    /**
     * 校验是否为class文件
     *
     * @param name
     * @return
     */
    private boolean validate(String name) {
        if (name == null) return false;
        if (!name.endsWith(".class")) return false;

        return true;
    }

}
