package creating_object;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.time.LocalDateTime;

// It allows to serialize from LocalDateTime to String

public class SerializerFromLocalDateTimeToString extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime value, JsonGenerator generator, SerializerProvider provider){

        try {
            generator.writeString(value.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
