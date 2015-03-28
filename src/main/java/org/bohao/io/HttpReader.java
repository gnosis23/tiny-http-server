package org.bohao.io;

import org.bohao.exception.HttpException;
import org.bohao.proto.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by bohao on 03-28-0028.
 */
public class HttpReader {
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
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    parseFirst(request, line);
                    continue;
                }

                if (line.equals(""))
                    break;

                parseAttribute(request, line);
            }

            // TODO: header body
        } catch (HttpException | IOException e) {
            logger.error(e.getMessage());
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
}
