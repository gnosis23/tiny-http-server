package org.bohao.core;

import org.bohao.annotation.Controller;
import org.bohao.annotation.RequestMapping;
import org.bohao.entt.Cookie;
import org.bohao.proto.HttpRequest;
import org.bohao.proto.HttpResponse;
import org.bohao.utils.FileToStr;
import org.bohao.utils.TimeUtils;
import org.bohao.utils.UrlTools;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * 实验性controller
 * Created by bohao on 03-29-0029.
 */
@Controller(value = "/html")
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

    /**
     * 获取get参数：firstname, lastname
     */
    @RequestMapping(value = "/html/get")
    public void doGet3(HttpRequest request, HttpResponse response) {
        response.setStatus(200);

        DateTime now = new DateTime();
        response.setAttribute("Date", TimeUtils.getServerTime());
        response.setAttribute("Content-Type", "text/html");
        response.setAttribute("Connection", "keep-alive");
        response.setAttribute("Server", "Bengine");
        response.setAttribute("Last-Modified", response.getHeader("Date"));
        // 特殊用，测试
        response.setAttribute("Btag", "get");

        DateTime twoDays = now.plusDays(1);
        response.setAttribute("Expires", TimeUtils.toHttpTime(twoDays));

        logger.info(request.getContextPath());
        Map<String, String> map = UrlTools.parse(request.getContextPath());
        String ans = String.format("<p>%s</p><p>%s</p>", map.get("firstname"), map.get("lastname"));
        response.setContent(ans);
    }

    /**
     * cookie test
     */
    @RequestMapping(value = "/html/cookie")
    public void doGet4(HttpRequest request, HttpResponse response) {
        response.setStatus(200);

        DateTime now = new DateTime();
        response.setAttribute("Date", TimeUtils.getServerTime());
        response.setAttribute("Content-Type", "text/html");
        response.setAttribute("Connection", "keep-alive");
        response.setAttribute("Server", "Bengine");
        response.setAttribute("Last-Modified", response.getHeader("Date"));
        // 特殊用，测试
        response.setAttribute("Btag", "get");

        DateTime twoDays = now.plusDays(1);
        response.setAttribute("Expires", TimeUtils.toHttpTime(twoDays));

        logger.info(request.getContextPath());

        List<Cookie> cookies = request.getCookies();
        Cookie cookie;

        if (cookies.size() > 0) {
            cookie = cookies.get(0);
        } else {
            cookie = new Cookie("sid", "1");
        }
        String ans = String.format("<p>cookie0[%s=%s]</p>", cookie.getName(), cookie.getValue());

        cookie.setValue(String.valueOf(Integer.valueOf(cookie.getValue()) + 1));
        response.addCookie(cookie);

        response.setContent(ans);
    }

    /**
     * 获取post的参数
     */
    @RequestMapping(value = "/html/post", method = "POST")
    public void doPost(HttpRequest request, HttpResponse response) {
        response.setStatus(200);

        DateTime now = new DateTime();
        response.setAttribute("Date", TimeUtils.getServerTime());
        response.setAttribute("Content-Type", "text/html");
        response.setAttribute("Connection", "keep-alive");
        response.setAttribute("Server", "Bengine");
        response.setAttribute("Last-Modified", response.getHeader("Date"));
        // 特殊用，测试
        response.setAttribute("Btag", "post");

        DateTime twoDays = now.plusDays(2);
        response.setAttribute("Expires", TimeUtils.toHttpTime(twoDays));

        logger.info(request.getContextPath());
        Map<String, String> map = UrlTools.parsePost(request.getContent());
        String ans = String.format("<p>%s</p><p>%s</p>", map.get("firstname"), map.get("lastname"));
        response.setContent(ans);
    }
}
