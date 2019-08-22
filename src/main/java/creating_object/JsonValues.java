package creating_object;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

public class JsonValues {


    // Return json with changed value
    // Key: or "/fundingAccountInfo/encryptedPayload/encryptedData/TokenData" or "expiryMonth"

    public String changeValue(String key, Object value, Object object) {

        JsonNode newNode = new ObjectMapper().convertValue(value, JsonNode.class);
        ObjectNode objectNode = new ObjectMapper().valueToTree(object);

        if (key.contains("/")) {

            String parentParam = key.substring(0, key.lastIndexOf("/"));
            String param = key.substring(key.lastIndexOf("/") + 1);
            objectNode.at(parentParam).findParent(param).fields().forEachRemaining(p -> {

                if (p.getKey().equals(param)) {
                    p.setValue(newNode);
                }
            });
        } else {
            objectNode.findParent(key).set(key, newNode);        }

        return objectNode.toString();
    }

    // Return json with deleted parameter
    public String removeKey(String key, Object object) {

        ObjectNode objectNode = new ObjectMapper().valueToTree(object);

        if (key.contains("/")) {

            String parentParam = key.substring(0, key.lastIndexOf("/"));
            String param = key.substring(key.lastIndexOf("/") + 1);
            ObjectNode newObjectNode = (ObjectNode) objectNode.at(parentParam).findParent(param);
            return newObjectNode.remove(param).toString();

        } else {
            return objectNode.findParent(key).remove(key).toString();
        }
    }

    public static void main(String[] args) {

    }
}
