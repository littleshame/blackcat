package com.blackcat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: tjc
 * @Date: 2019-7-1
 */
public class Boostrap {

    public static void main(String[] args) {

        String webroot = "target/classes";

        StandardContext context = new StandardContext();
        context.start();


        DefaultConnector  connector = new DefaultConnector();
        connector.setContext(context);
        connector.start();

    }

}
