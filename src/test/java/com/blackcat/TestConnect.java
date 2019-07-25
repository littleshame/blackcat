package com.blackcat;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author: tjc
 * @Date: 2019-7-9
 */
public class TestConnect {

    /**
     * 模拟客户端连接blackcat（需要先启动blackcat）
     */
    @Test
    public void testClientConnect(){

        try {
            Socket socket = new Socket("127.0.0.1",8080);
            OutputStream out = socket.getOutputStream();
            out.write("hello".getBytes());

            InputStream input = socket.getInputStream();
            byte[] back = new byte[1024];
            int i = input.read(back);

            for (int j = 0; j < i; j++) {
                System.out.print((char) back[j]);
            }
            System.out.println();
            input.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoader(){
        URL[] urls = null;
        try {
            urls = new URL[]{
                new URL("file:D:/IntelliJ IDEA 2017.1.6/workspace/blackcat/WEB-INF/classes")
            };
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        URLClassLoader urlClassLoader = new URLClassLoader(urls);
        try {
            Class<?> clazz = urlClassLoader.loadClass("com.blackcat.Bean");
            System.out.println(clazz.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRecurveURL(){
        File dir = new File("D:/IntelliJ IDEA 2017.1.6/workspace/blackcat/WEB-INF/classes");
        recurvePrint(dir);
    }

    public void recurvePrint(File parentDir){
        File[] sonfiles = parentDir.listFiles();
        for(File son : sonfiles) {
            if(!son.isFile()) {
                recurvePrint(son);
                continue;
            }
            System.out.println(son.getName());
        }
    }
}
