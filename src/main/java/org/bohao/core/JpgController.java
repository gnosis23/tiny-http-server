package org.bohao.core;

import org.bohao.annotation.Controller;
import org.bohao.annotation.RequestMapping;
import org.bohao.proto.HttpRequest;
import org.bohao.proto.HttpResponse;
import org.bohao.utils.FileToStr;
import org.bohao.utils.TimeUtils;
import org.joda.time.DateTime;

/**
 * Created by bohao on 04-02-0002.
 */
@Controller(value = "/image")
public class JpgController {

    @RequestMapping(value = "/image")
    public void doGet(HttpRequest request, HttpResponse response) {
        response.setStatus(200);

        DateTime now = new DateTime();
        response.setAttribute("Date", TimeUtils.getServerTime());
        response.setAttribute("Content-Type", "image/jpeg");
        response.setAttribute("Connection", "keep-alive");
        response.setAttribute("Server", "Bengine");
        response.setAttribute("Last-Modified", response.getHeader("Date"));
        response.setAttribute("Accept-Ranges", "bytes");

        DateTime twoDays = now.plusDays(2);
        response.setAttribute("Expires", TimeUtils.toHttpTime(twoDays));

        // TODO: 看来对图片String类型不行
        response.setContent(FileToStr.resolve("web-root" + request.getContextPath()));
    }
}
