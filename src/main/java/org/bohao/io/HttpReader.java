package org.bohao.io;

import org.bohao.entt.Cookie;
import org.bohao.exception.HttpException;
import org.bohao.proto.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bohao on 03-28-0028.
 */
public class HttpReader implements AutoCloseable {
    private static Logger logger = LoggerFactory.getLogger(HttpReader.class);
    private BufferedReader reader;

    public HttpReader(InputStream input) {
        reader = new BufferedReader(new InputStreamReader(input));
    }

    public HttpRequest getRequest() {
        HttpRequest request = new HttpRequest();
        try {
            String line;
            boolean firstLine = true;
            List<Cookie> cookies = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    parseFirst(request, line);
                    continue;
                }

                if (line.equals(""))
                    break;
                else if (line.startsWith("Cookie:")) {
                    Cookie cookie = parseCookie(line);
                    cookies.add(cookie);
                } else {
                    parseAttribute(request, line);
                }
            }

            request.setCookies(cookies);

            // buffered reader 不会在另一边没关闭的时候结束，所以会挂着等待
            // 那么我们怎么能断定已经读到最后了呢？
            // 只能靠Content-Length
            // 你没指定，我就当你结束了。
            if (request.getHeader("Content-Length") != null) {
                int len = Integer.valueOf(request.getHeader("Content-Length"));
                char[] buff = new char[len];
                reader.read(buff, 0, len);
                request.setContent(String.valueOf(buff));
            }

        } catch (HttpException | IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }

        return request;
    }

    /**
     * ex :
     * Host: localhost:22222
     * User-Agent: Mozilla/5.0 (Windows NT 6.3; WOW64; rv:36.0) Gecko/20100101 Firefox/36.0
     */
    private void parseAttribute(HttpRequest request, String line) throws HttpException {
        int index = line.indexOf(":");
        if (index == -1) {
            throw new HttpException("http format error");
        }

        String headerName = line.substring(0, index);
        // remove \n
        String headerValue = line.substring(index + 2, line.length());
        logger.debug("({})=({})", headerName, headerValue);
        request.setAttribute(headerName, headerValue);
    }

    /**
     * eg: GET / HTTP/1.1
     *
     * @param request request
     * @throws HttpException
     */
    private void parseFirst(HttpRequest request, String line) throws HttpException {
        String[] strs = line.split(" ");
        if (strs.length != 3) {
            throw new HttpException("Http first header");
        }

        request.setMethod(strs[0]);
        request.setContextPath(strs[1]);

    }

    @Override
    public void close() throws Exception {
        reader.close();
    }

    /**
     * parse cookie
     * eg: Cookie: $Version=1; Customer=WILE_E_COYOTE; $Path=/acme
     * version, path
     *
     * @param line starts with "Cookie: "
     * @return cookie
     */
    protected Cookie parseCookie(String line) throws HttpException {
        if (!line.startsWith("Cookie:")) {
            throw new HttpException("not a cookie");
        }

        Cookie cookie = new Cookie();

        String tokens = line.substring(8);
        for (String token : tokens.split(";")) {
            String[] t = token.trim().split("=");

            switch (t[0].trim()) {
                case "$Version":
                case "Version":
                    cookie.setVersion(Integer.parseInt(t[1]));
                    break;
                case "$Path":
                case "Path":
                    cookie.setPath(t[1]);
                    break;
                case "$Domain":
                case "Domain":
                    cookie.setDomain(t[1]);
                    break;
                case "$Max-Age":
                case "Max-Age":
                    cookie.setMaxAge(Integer.parseInt(t[1]));
                    break;
                case "$Comment":
                case "Comment":
                    cookie.setComment(t[1]);
                    break;
                default:
                    if (t[0].startsWith("$"))
                        throw new HttpException("attribute error");

                    cookie.setName(t[0]);
                    cookie.setValue(t[1]);
            }
        }

        logger.debug("cookie - {}", cookie);
        return cookie;
    }
}
