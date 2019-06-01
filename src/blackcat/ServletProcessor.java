package blackcat;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

/**
 * @author: tjc
 * @Date: 2019-5-31
 */
public class ServletProcessor {
    public void process(Request request, Response response) throws IllegalAccessException, InstantiationException {
        String uri = request.getUri();
        //获取servlet名,只允许/servlet/servletName一层路由
        String servletName = uri.substring(uri.lastIndexOf("/") + 1);
        URLClassLoader loader = null;
        try {
            //拼接完整路径，找到对应资源
            URL[] urls = new URL[1];
            URLStreamHandler streamHandler = null;
            //根目录
            File classPath = new File(Constants.WEB_ROOT);
            String repository = (new URL("file", null, classPath.getCanonicalPath() +
                    File.separator)).toString();
            //System.out.println(repository);
            urls[0] = new URL(null, repository, streamHandler);
            loader = new URLClassLoader(urls);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Class myClass = null;
        try {
            myClass = loader.loadClass(servletName);
        } catch (ClassNotFoundException e) {
            System.out.println(e.toString());
        }

        Servlet servlet = null;
        servlet = (Servlet) myClass.newInstance();
        try {
            servlet.service((ServletRequest)request, (ServletResponse) response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
