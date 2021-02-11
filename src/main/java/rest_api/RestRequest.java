package rest_api;

import http.Method;
import java.util.Map;

public interface RestRequest {


    Method getMethod();
    String getUrl();
    String getPath();
    String getPathWithParam();
    Map<String, String> getHeaders();
    Map<String, String> getCookie();
    Integer getConnectTimeout();
    Integer getSocketTimeout();
    Object getRequestBody();
    Map<String, Object> getRequestParameters();

    RestRequest method(Method method);
    RestRequest url(String url);
    RestRequest path(String path);
    RestRequest pathWithParam(String pathWithParam);
    RestRequest headers(Map<String, String> headers);
    RestRequest cookie(Map<String, String> cookies);
    RestRequest connectTimeout(Integer connectTimeout);
    RestRequest socketTimeout(Integer socketTimeout);
    RestRequest requestBody(Object requestObject);
    RestRequest requestParameters(Map<String, Object> parameters);
    RestRequest httpImplementation(HttpImplementation httpImplementation);
    RestResponse send();

    static void expect(long seconds) {

        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
