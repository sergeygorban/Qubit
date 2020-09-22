package creating_object;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import time.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// It allows to deserialize from String to LocalDateTime

public class DeserializerFromStringToLocalDate_1 extends JsonDeserializer<LocalDate> {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateTimeFormat.ISO_6.getFormat());


    @Override
    public LocalDate deserialize(JsonParser jsonparser, DeserializationContext context){

        try {

            return LocalDate.parse(jsonparser.getText(), formatter);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
