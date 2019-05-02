package creating_object;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class ObjectToMap {

    public Map createRequestParamFromObject(Object object){

        try {
            return new ObjectMapper().convertValue(object, Map.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
