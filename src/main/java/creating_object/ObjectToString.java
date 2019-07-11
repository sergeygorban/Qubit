package creating_object;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ObjectToString {

    public String createRequestObject(Object object){

        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String createRequestObjectWithRootValue(Object object){

        try {
            return new ObjectMapper().enable(SerializationFeature.WRAP_ROOT_VALUE).writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
