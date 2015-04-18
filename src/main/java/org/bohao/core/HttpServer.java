package org.bohao.core;


import org.bohao.io.HttpReader;
import org.bohao.io.HttpWriter;
import org.bohao.proto.HttpRequest;
import org.bohao.proto.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by bohao on 03-28-0028.
 */
public class HttpServer {
    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);

    private ServerSocket serverSocket;

    public HttpServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException {
        ExecutorService executor = Executors.newCachedThreadPool();

        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();

                Task task = new Task(clientSocket);
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

    private class Task implements Runnable {
        public Socket clientSocket;

        public Task(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try (HttpReader in = new HttpReader(clientSocket.getInputStream());
                 HttpWriter out = new HttpWriter(clientSocket.getOutputStream())
            ) {
                HttpRequest request = in.getRequest();
                HttpResponse response = new HttpResponse();

                ControlResolver resolver = new ControlResolver();
                resolver.process(request, response);

                out.sendResponse(response);
            } catch (Exception e) {
                // here when disconnect
                logger.info("Exception caught when trying to listen on port "
                        + serverSocket.getInetAddress()
                        + " or listening for a connection");
                logger.info(e.getMessage());
            }
        }
    }
}
