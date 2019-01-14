package http;

import java.util.Map;

public interface RequestObject {

    String getRequestObject();
    Map<String, String> getRequestParameters();
}
