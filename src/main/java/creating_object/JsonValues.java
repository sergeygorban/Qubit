package creating_object;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import http.RequestObject;

public class JsonValues {


    // Return json with changed value
    // Key: or "/fundingAccountInfo/encryptedPayload/encryptedData/TokenData" or "expiryMonth"
    // It is used for the value change of the parameters with value Object

    public String changeValue(String jsonPath, Object value, Object object) {

        JsonNode newNode = new ObjectMapper().convertValue(value, JsonNode.class);
        ObjectNode objectNode = new ObjectMapper().valueToTree(object);

        if (jsonPath.contains("/")) {

            String parentPath = jsonPath.substring(0, jsonPath.lastIndexOf("/"));
            String param = jsonPath.substring(jsonPath.lastIndexOf("/") + 1);

            objectNode.at(parentPath).fields().forEachRemaining(keyValue -> {
                if (keyValue.getKey().equals(param)) {
                    keyValue.setValue(newNode);
                }
            });

            return objectNode.toString();
        }

        return objectNode.findParent(jsonPath).set(jsonPath, newNode).toString();
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
