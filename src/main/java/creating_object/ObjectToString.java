package creating_object;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectToString {

    public String createRequestObject(Object object){

        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
