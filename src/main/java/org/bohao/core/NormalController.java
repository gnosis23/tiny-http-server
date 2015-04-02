package org.bohao.core;

import org.bohao.annotation.Controller;
import org.bohao.annotation.RequestMapping;
import org.bohao.proto.HttpRequest;
import org.bohao.proto.HttpResponse;
import org.bohao.utils.FileToStr;
import org.bohao.utils.TimeUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 实验性controller
 * Created by bohao on 03-29-0029.
 */
@Controller(value = "/")
public class NormalController {
    private static final Logger logger = LoggerFactory.getLogger(NormalController.class);

    @RequestMapping(value = "/html")
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

        logger.info(request.getContextPath());
        response.setContent(FileToStr.resolve("web-root" + request.getContextPath()));
    }

    @RequestMapping(value = "/html/form")
    public void doGet2(HttpRequest request, HttpResponse response) {
        response.setStatus(200);

        DateTime now = new DateTime();
        response.setAttribute("Date", TimeUtils.getServerTime());
        response.setAttribute("Content-Type", "text/html");
        response.setAttribute("Connection", "keep-alive");
        response.setAttribute("Server", "Bengine");
        response.setAttribute("Last-Modified", response.getHeader("Date"));

        DateTime twoDays = now.plusDays(2);
        response.setAttribute("Expires", TimeUtils.toHttpTime(twoDays));

        logger.info(request.getContextPath());
        response.setContent(FileToStr.resolve("web-root" + request.getContextPath()));
    }
}
