package ru.yandex.practicum.api;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.time.Duration;

public class DurationAdapter implements JsonSerializer<Duration>, JsonDeserializer<Duration> {

    @Override
    public Duration deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) {
        int src = jsonElement.getAsJsonPrimitive().getAsInt();
        return Duration.ofSeconds(src);
    }

    @Override
    public JsonElement serialize(Duration duration,
                                 Type type, JsonSerializationContext context) {
        int hours = (int) duration.toSeconds();
        return new JsonPrimitive(hours);
    }
}

