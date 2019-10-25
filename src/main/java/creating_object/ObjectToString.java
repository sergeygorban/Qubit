package creating_object;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

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


    public String createRequestObjectAsXml(Object object){

        try {
            return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + new XmlMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
