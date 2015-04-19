package org.bohao.proto;

import org.bohao.entt.Cookie;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bohao on 03-28-0028.
 */
public class HttpRequest {
    private String contextPath = "/";
    private String method = "GET";
    private Map<String, String> headers = new HashMap<>();
    private String content = "";
    private List<Cookie> cookies;
    private HttpSession session;

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

    /**
     * @return normal post content
     */
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCookies(List<Cookie> cookies) {
        this.cookies = cookies;
    }

    public List<Cookie> getCookies() {
        return cookies;
    }

    /**
     * Returns the current HttpSession associated with this request or, if there is no current session
     * and create is true, returns a new session.
     *
     * @param create true to create a new session for this request if necessary;
     *               false to return null if there's no current session
     * @return the HttpSession associated with this request or null if create is false and the request
     * has no valid session
     */
    public HttpSession getSession(boolean create) {
//        if (create && session == null) {
//            //create
//        }

        return session;
    }

    public HttpSession getSession() {
        // create if null

        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }
}
