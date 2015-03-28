package org.bohao;

import org.bohao.core.HttpServer;

import java.io.IOException;

/**
 * Created by bohao on 03-27-0027.
 */
public class app {
    public static void main(String[] args) {
        try {
            HttpServer server = new HttpServer(22222);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
