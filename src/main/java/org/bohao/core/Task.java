package org.bohao.core;

import org.bohao.entt.Cookie;
import org.bohao.io.HttpReader;
import org.bohao.io.HttpWriter;
import org.bohao.proto.HttpRequest;
import org.bohao.proto.HttpResponse;
import org.bohao.proto.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by bohao on 15/04/19.
 */
public class Task implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Task.class);

    public Socket clientSocket;
    public Map<String, HttpSession> allSession;

    public Task(Socket clientSocket, ConcurrentHashMap<String, HttpSession> allSession) {
        this.clientSocket = clientSocket;
        this.allSession = allSession;
    }

    @Override
    public void run() {
        try (HttpReader in = new HttpReader(clientSocket.getInputStream());
             HttpWriter out = new HttpWriter(clientSocket.getOutputStream())
        ) {
            HttpRequest request = in.getRequest();
            HttpResponse response = new HttpResponse();

            manageSession(request, response);

            ControlResolver resolver = new ControlResolver();
            resolver.process(request, response);

            out.sendResponse(response);
        } catch (Exception e) {
            // here when disconnect
            logger.info("Exception caught when trying to listen on port "
                    + " or listening for a connection");
            logger.info(e.getMessage());
        }
    }

    protected void manageSession(HttpRequest request, HttpResponse response) {
        // session related
        // check sid
        HttpSession mySession = null;

        try {
            Cookie c = request.getCookies().stream().filter(
                    x -> x.getName().equals("sid")).findFirst().get();
            mySession = allSession.get(c.getValue());
        } catch (NoSuchElementException | NullPointerException ignored) {
        }

        if (mySession == null) {
            int n = allSession.size() + 1;
            mySession = new HttpSession(String.valueOf(n) + System.currentTimeMillis());
            mySession.setAttribute("cnt", 0);
            allSession.put(mySession.getId(), mySession);
            response.addCookie(new Cookie("sid", mySession.getId()));
        }

        request.setSession(mySession);
    }
}