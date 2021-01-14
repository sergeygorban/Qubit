package rest_api;

import http.Method;
import org.apache.http.cookie.Cookie;

import java.util.Map;

public interface RestRequest {


    Method getMethod();
    Integer getConnectTimeout();
    Integer getSocketTimeout();
    String getUrl();
    String getPath();
    Map<String, String> getHeaders();
    Map<String, String> getCookie();
    Object getRequestBody();
    Map<String, Object> getRequestParameters();

    RestRequest method(Method method);
    RestRequest connectTimeout(Integer connectTimeout);
    RestRequest socketTimeout(Integer socketTimeout);
    RestRequest url(String url);
    RestRequest path(String path);
    RestRequest headers(Map<String, String> headers);
    RestRequest cookie(Map<String, String> cookies);
    RestRequest requestBody(Object requestObject);
    RestRequest requestParameters(Map<String, Object> parameters);
    RestRequest httpImplementation(HttpImplementation httpImplementation);
    RestResponse send();

}
