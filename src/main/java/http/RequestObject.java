package http;

import java.util.Map;

public interface RequestObject {

    String getRequestObject();
    Map<String, Object> getRequestParameters();

    // For encrypting data inside а request object
    void encryptData();

}
