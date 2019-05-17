package creating_object;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// It allows to deserialize from String to LocalDateTime

public class DeserializerFromStringToLocalDate extends JsonDeserializer<LocalDate> {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public LocalDate deserialize(JsonParser jsonparser, DeserializationContext context){

        try {

            return LocalDate.parse(jsonparser.getText(), formatter);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
