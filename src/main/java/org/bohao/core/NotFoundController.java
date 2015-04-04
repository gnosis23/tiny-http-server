package org.bohao.core;

import org.bohao.proto.HttpRequest;
import org.bohao.proto.HttpResponse;
import org.bohao.utils.TimeUtils;
import org.joda.time.DateTime;

/**
 * Created by bohao on 04-02-0002.
 */
public class NotFoundController {
    public void doGet(HttpRequest request, HttpResponse response) {
        response.setStatus(404);

        DateTime now = new DateTime();
        response.setAttribute("Date", TimeUtils.getServerTime());
        response.setAttribute("Content-Type", "text/html");
        response.setAttribute("Connection", "keep-alive");
        response.setAttribute("Server", "Bengine");
        response.setAttribute("Last-Modified", response.getHeader("Date"));

        DateTime twoDays = now.plusDays(2);
        response.setAttribute("Expires", TimeUtils.toHttpTime(twoDays));

        response.setContent("<html><h1>404</h1><img src='/image/404.png'></img></html>");
    }
}
