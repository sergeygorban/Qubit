package rest_api;

import java.nio.charset.Charset;
import java.util.Map;

public interface RestResponse {

    RestResponse charset(Charset charset);

    Integer getResponseCode();
    Map<String, String> getHeaders();
    String getBody();
    <T> T getBodyAs(Class<T> cls);

}
