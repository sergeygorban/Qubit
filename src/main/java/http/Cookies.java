package http;

import org.apache.http.cookie.Cookie;

import java.util.Date;

public class Cookies implements Cookie {

    private String name;
    private String value;
    private Date expiryDate;
    private String domain;
    private String path = "/";

    // Returns false if the cookie should be discarded at the end of the "session"; true otherwise.
    private boolean persistent = false;

    // Indicates whether this cookie requires a secure connection.
    private boolean secure = false;

    // Returns true if this cookie has expired.
    private boolean expired = false;

    public Cookies(String name, String value, String domain) {
        this.name = name;
        this.value = value;
        this.domain = domain;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getComment() {
        return null;
    }

    @Override
    public String getCommentURL() {
        return null;
    }

    @Override
    public Date getExpiryDate() {
        return this.expiryDate;
    }

    @Override
    public boolean isPersistent() {
        return this.persistent;
    }

    @Override
    public String getDomain() {
        return this.domain;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public int[] getPorts() {
        return null;
    }

    @Override
    public boolean isSecure() {
        return this.secure;
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public boolean isExpired(Date date) {
        return null != this.expiryDate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(name).append(" = ").append(value);
        sb.append(", expiryDate=").append(expiryDate);
        sb.append(", domain=").append(domain);
        sb.append(", path=").append(path);
        sb.append(", persistent=").append(persistent);
        sb.append(", secure=").append(secure);
        sb.append(", expired=").append(expired);
        return sb.toString();
    }
}
