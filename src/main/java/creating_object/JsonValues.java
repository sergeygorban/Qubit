package creating_object;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

public class JsonValues {


    public JsonNode changeValue(String parameter, String value, Object object) {

        ObjectNode objectNode = new ObjectMapper().valueToTree(object);
        return objectNode.findParent(parameter).set(parameter, value == null ? null : new TextNode(value));
    }
}
