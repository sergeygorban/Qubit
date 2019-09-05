package http;

import java.util.Map;

public interface RequestObject {

    String getRequestObject();
    default Map<String, Object> getRequestParameters() {
        return null;
    };

    // For encrypting data inside а request object
    default Object encryptDataAsObject(){
        return null;
    };
    default void encryptData() {

    };


}
