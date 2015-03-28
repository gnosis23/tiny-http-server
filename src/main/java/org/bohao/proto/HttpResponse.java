package org.bohao.proto;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bohao on 03-28-0028.
 */
public class HttpResponse {
    private Map<String, String> headers = new HashMap<>();
    private String content;
    private int status;

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
}
