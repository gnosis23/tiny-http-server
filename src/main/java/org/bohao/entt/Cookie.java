package org.bohao.entt;

/**
 * The servlet sends cookies to the browser by using the
 * HttpServletResponse.addCookie(javax.servlet.http.Cookie) method, which adds fields to HTTP response
 * headers to send cookies to the browser, one at a time.
 * The browser is expected to support 20 cookies for each Web server, 300 cookies total,
 * and may limit cookie size to 4 KB each.
 * The browser returns cookies to the servlet by adding fields to HTTP request headers.
 * Cookies can be retrieved from a request by using the HttpServletRequest.getCookies() method.
 * Several cookies might have the same name but different path attributes.
 */
public class Cookie {
    private String comment;
    private String domain;
    private int maxAge;
    private String name;
    private String path;
    private boolean secure;
    private String value;
    private int version;

    public Cookie() {
    }

    // name cannot be changed after creation
    public Cookie(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public String getName() {
        return name;
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Cookie{" +
                "comment='" + comment + '\'' +
                ", domain='" + domain + '\'' +
                ", maxAge=" + maxAge +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", secure=" + secure +
                ", value='" + value + '\'' +
                ", version=" + version +
                '}';
    }
}
