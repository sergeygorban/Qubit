package creating_object;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class SqlToObject {

    public static <T extends SqlToObject> T sqlObjectToJava(ResultSet resultSet, Class<T> cl) {

        Map<String, Object> map = new LinkedHashMap<>();

        try {

            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                map.put(resultSet.getMetaData().getColumnName(i),resultSet.getObject(i));
            }
            return new ObjectMapper().convertValue(map, cl);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
