package org.bohao.proto;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by bohao on 15/04/19.
 */
public class HttpSession {
    private Map<String, Object> attrs = new ConcurrentHashMap<>();
    private long createTime;
    private String sid;

    public HttpSession(String no) {
        createTime = System.currentTimeMillis();
        sid = no;
    }

    public Object getAttribute(String name) {
        return attrs.get(name);
    }

    public void removeAttribute(String name) {
        attrs.remove(name);
    }

    public void setAttribute(String name, Object value) {
        attrs.put(name, value);
    }

    public long getCreationTime() {
        return createTime;
    }

    public String getId() {
        return sid;
    }

    public Enumeration<String> getAttributeNames() {
        return Collections.enumeration(attrs.keySet());
    }
}
