package creating_object;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// It allows to serialize from LocalDateTime to String

public class SerializerFromLocalDateTimeToString extends JsonSerializer<LocalDateTime> {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @Override
    public void serialize(LocalDateTime value, JsonGenerator generator, SerializerProvider provider){

        try {
            generator.writeString(value.format(formatter));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
