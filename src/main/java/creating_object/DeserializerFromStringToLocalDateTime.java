package creating_object;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// It allows to deserialize from String to LocalDateTime
public class DeserializerFromStringToLocalDateTime extends JsonDeserializer<LocalDateTime> {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public LocalDateTime deserialize(JsonParser jsonparser, DeserializationContext context)
            throws IOException, JsonProcessingException {

        try {
            return LocalDateTime.parse(jsonparser.getText(), formatter);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
