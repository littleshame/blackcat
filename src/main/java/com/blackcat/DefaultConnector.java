package com.blackcat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author: tjc
 * @Date: 2019-7-12
 */
public class DefaultConnector {

    StandardContext context = null;

    DefaultProcessor defaultProcessor = null;


    private boolean started = false;

    public void start() {
        int port = 8080;

        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(port);
            started = true;
            System.out.println("服务器开启... 端口：" + port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //监听并且传递socket可以使用线程去做
        while (started) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //tomcat应该维护一个processor实例池，简化
            DefaultProcessor processor = new DefaultProcessor(this);
            //processor获取到了socket就应该立即返回，然后右processor异步处理，这里简化了
            processor.process(socket);


            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public StandardContext getContext() {
        return this.context;
    }

    public void setContext(StandardContext context) {
        this.context = context;
    }
}
