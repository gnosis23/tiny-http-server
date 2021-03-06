package org.bohao.io;

import org.bohao.entt.Cookie;
import org.bohao.proto.HttpResponse;
import org.bohao.utils.TimeUtils;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * Created by bohao on 03-28-0028.
 */
public class HttpWriter implements AutoCloseable {
    // 为什么不用PrintWritwr？
    // 因为http内可能存放图片等二进制信息
    // PrintWriter是基于字符类型的，无法直接输出二进制
    private OutputStream writer;

    public HttpWriter(OutputStream outputStream) {
        writer = new BufferedOutputStream(outputStream);
    }

    @Override
    public void close() throws Exception {
        writer.close();
    }

    public void sendResponse(HttpResponse response) {
        // header
        String desc = "";
        switch (response.getStatus()) {
            case 200:
                desc = "OK";
                break;
            case 404:
                desc = "Not Found";
                break;
        }

        try {
            writer.write(encodingStringWithUtf8(String.format("HTTP/1.1 %d %S\n", response.getStatus(), desc)));

            // attributes
            for (String name : response.getHeaderNames()) {
                writer.write(encodingStringWithUtf8(String.format("%s: %s\n", name, response.getHeader(name))));
            }

            for (Cookie cookie : response.getCookies()) {
                writer.write(encodingStringWithUtf8(print(cookie)));
            }
            writer.write(encodingStringWithUtf8("\n"));

            switch (response.getHeader("Content-Type")) {
                case "text/html":
                    writer.write(encodingStringWithUtf8(response.getContent()));
                    break;
                case "image/png":
                case "image/jpeg":
                case "application/json":
                    writer.write(response.getBinaryData());
                    break;
            }

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param str str -> bytes
     * @return bytes
     */
    private byte[] encodingStringWithUtf8(String str) {
        return str.getBytes(Charset.forName("UTF-8"));
    }

    /**
     * only print name and value by now.
     *
     * @param cookie .
     * @return print cookie
     */
    protected String print(Cookie cookie) {
        String str = String.format("Set-Cookie: %s=%s; expires=%s", cookie.getName(),
                cookie.getValue(), TimeUtils.nDaysLater(1));
        StringBuilder sb = new StringBuilder(str);

        if (cookie.getPath() != null) {
            sb.append("; Path=").append(cookie.getPath());
        }
        if (cookie.getDomain() != null) {
            sb.append("; Domain=").append(cookie.getDomain());
        }

        sb.append("\r\n");
        return sb.toString();
    }
}
