package org.bohao.core;


import org.bohao.proto.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by bohao on 03-28-0028.
 */
public class HttpServer {
    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);

    private ServerSocket serverSocket;
    private ConcurrentHashMap<String, HttpSession> sessions;

    public HttpServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        sessions = new ConcurrentHashMap<>();
    }

    public void start() throws IOException {
        ExecutorService executor = Executors.newCachedThreadPool();

        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();

                Task task = new Task(clientSocket, sessions);
                executor.execute(task);
            }
        } catch (Exception e) {
            // here when disconnect
            logger.info("Exception caught when trying to listen on port "
                    + serverSocket.getInetAddress()
                    + " or listening for a connection");
            logger.info(e.getMessage());
        } finally {
            serverSocket.close();
        }
    }

}
