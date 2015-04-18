package org.bohao.proto;

import org.bohao.entt.Cookie;

import java.util.*;

/**
 * Created by bohao on 03-28-0028.
 */
public class HttpResponse {
    private Map<String, String> headers = new HashMap<>();
    private String content;
    private int status;
    private byte[] binaryData;
    private List<Cookie> cookies = new ArrayList<>();

    public HttpResponse() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public void setAttribute(String name, String value) {
        headers.put(name, value);
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public Collection<String> getHeaderNames() {
        return headers.keySet();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte[] getBinaryData() {
        return binaryData;
    }

    public void setBinaryData(byte[] binaryData) {
        this.binaryData = binaryData;
    }

    public void addCookie(Cookie cookie) {
        cookies.add(cookie);
    }

    public List<Cookie> getCookies() {
        return cookies;
    }
}
