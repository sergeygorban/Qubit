package creating_object;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import time.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// It allows to deserialize from String to LocalDateTime

public class DeserializerFromStringToLocalDateTime extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser jsonparser, DeserializationContext context){

        try {
            return LocalDateTime.parse(jsonparser.getText(), DateTimeFormatter.ofPattern(DateTimeFormat.ISO_1.getFormat()));

        } catch (Exception e) {

            try {
                return LocalDateTime.parse(jsonparser.getText(), DateTimeFormatter.ofPattern(DateTimeFormat.ISO_3.getFormat()));
            } catch (Exception e1) {
                throw new RuntimeException(e1);
            }
        }
    }
}
