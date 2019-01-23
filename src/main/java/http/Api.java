package http;
import org.apache.http.cookie.Cookie;

import java.util.Map;

public interface Api {

    Method getMethod();
    String getUrl();
    Map<String, String> getHeaders();
    Cookie getCoocie();
    Class getClassForResponse();

    default long getWaiting() {
        return 8000;
    };
    default String geCharset() {
        return "UTF-8";
    }
    default int getConnectTimeout() {
        return 3000;
    };
    default int getSocketTimeout() {
        return 8000;
    };

    static void expect(int seconds) {

        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    enum Method {
        POST,
        GET;
    }
}
