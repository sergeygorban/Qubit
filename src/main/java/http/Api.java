package http;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;

import java.util.Map;

public interface Api {

    Method getMethod();
    String getUrl();
    void setUrl(String url);
    Map<String, String> getHeaders();
    Class getClassForResponse();

    default long getWaiting() {
        return 8000;
    };
    default ContentType getContentType() {
        return null;
    }
    default int getConnectTimeout() {
        return 3000;
    };
    default int getSocketTimeout() {
        return 8000;
    };
    default Cookie getCookie() {
        return null;
    };
    default Map<String, ?> getValuesFromUrl() {
        return null;
    }

    static void expect(long seconds) {

        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    enum Method {
        POST,
        DELETE,
        PUT,
        GET;
    }
}
