package creating_object;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import time.DateTimeFormat;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

// It allows to deserialize from String to LocalDateTime

public class DeserializerFromTimeStampToLocalDateTime extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser jsonparser, DeserializationContext context){

        try {
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(jsonparser.getLongValue()), ZoneOffset.ofHours(2));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
