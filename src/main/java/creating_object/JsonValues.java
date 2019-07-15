package creating_object;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

public class JsonValues {


    // Return json with changed value
    public String changeValue(String parameter, String value, Object object) {

        ObjectNode objectNode = new ObjectMapper().valueToTree(object);
        objectNode.findParent(parameter).set(parameter, value == null ? null : new TextNode(value));
        return objectNode.toString();
    }

    // Return json with deleted parameter
    public String removeParameter(String parameter, Object object) {

        ObjectNode objectNode = new ObjectMapper().valueToTree(object);
        objectNode.findParent(parameter).remove(parameter);
        return objectNode.toString();
    }
}
