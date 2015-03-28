package org.bohao.proto;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bohao on 03-28-0028.
 */
public class HttpRequest {
    private String contextPath = "/";
    private String method;
    private Map<String, String> headers = new HashMap<>();

    public HttpRequest() {

    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * @param name  name of header
     * @param value header value
     */
    public void setAttribute(String name, String value) {
        headers.put(name, value);
    }

    /**
     * a specific http header
     *
     * @param name header name
     * @return string or null
     */
    public String getHeader(String name) {
        return headers.get(name);
    }

    public String getMethod() {
        return method;
    }
}
