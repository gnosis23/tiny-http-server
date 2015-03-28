package org.bohao.core;

import org.bohao.proto.HttpRequest;
import org.bohao.proto.HttpResponse;
import org.bohao.utils.TimeUtils;
import org.joda.time.DateTime;

/**
 * 实验性controller
 * Created by bohao on 03-29-0029.
 */
public class NormalController {
    public void doGet(HttpRequest request, HttpResponse response) {
        response.setStatus(200);

        DateTime now = new DateTime();
        response.setAttribute("Date", TimeUtils.getServerTime());
        response.setAttribute("Content-Type", "text/html");
        response.setAttribute("Connection", "keep-alive");
        response.setAttribute("Server", "Bengine");
        response.setAttribute("Last-Modified", response.getHeader("Date"));

        DateTime twoDays = now.plusDays(2);
        response.setAttribute("Expires", TimeUtils.toHttpTime(twoDays));

        // TODO: WOW SHIT
        response.setContent("<!DOCTYPE html PUBLIC \"-//IETF//DTD HTML 2.0//EN\">\n" +
                "<HTML>\n" +
                "   <HEAD>\n" +
                "      <TITLE>\n" +
                "         A Small Hello \n" +
                "      </TITLE>\n" +
                "   </HEAD>\n" +
                "<BODY>\n" +
                "   <H1>Hi</H1>\n" +
                "   <P>This is very minimal \"hello world\" HTML document.</P> \n" +
                "</BODY>\n" +
                "</HTML>");


    }
}
