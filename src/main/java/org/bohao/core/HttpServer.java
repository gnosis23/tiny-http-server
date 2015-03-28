package org.bohao.core;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by bohao on 03-28-0028.
 */
public class HttpServer {
    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);

    private ServerSocket serverSocket;

    public HttpServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void start() {
        while (true) {
            try (
                    Socket clientSocket = serverSocket.accept();
                    PrintWriter out =
                            new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(clientSocket.getInputStream()))
            ) {

                String inputLine, outputLine;

                // echo server now.
                while ((inputLine = in.readLine()) != null) {
                    outputLine = inputLine;
                    out.println(outputLine);
                    logger.debug("Get: " + outputLine);
                }
            } catch (IOException e) {
                // here when disconnect
                logger.info("Exception caught when trying to listen on port "
                        + serverSocket.getInetAddress()
                        + " or listening for a connection");
                logger.info(e.getMessage());
            }
        }
    }
}
