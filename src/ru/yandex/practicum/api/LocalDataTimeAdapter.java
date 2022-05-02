package ru.yandex.practicum.api;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDataTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
    @Override
    public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext
            jsonSerializationContext) {
        JsonObject object = new JsonObject();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy|HH:mm");
        object.addProperty("Date", localDateTime.format(formatter));
        return object;
    }

    @Override
    public LocalDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext
            jsonDeserializationContext) throws JsonParseException {
        return LocalDateTime
                .parse(jsonElement.getAsJsonObject().get("Date").getAsString(), DateTimeFormatter.ofPattern("dd.MM.yyyy|HH:mm"));
    }
}

