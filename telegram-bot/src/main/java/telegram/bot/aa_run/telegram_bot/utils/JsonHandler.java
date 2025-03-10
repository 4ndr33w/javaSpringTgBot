package telegram.bot.aa_run.telegram_bot.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class JsonHandler {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            //log.error("Error while converting object to json", e.getMessage());
            return Constants.ERROR_OCCURRED_MESSAGE;
        }
    }

    public static <T> T toObject(String json, Class<T> clazz) {
        try {
            return objectMapper.convertValue(json, clazz);
        } catch (ClassCastException e) {
            //log.error("Error while converting json to object", e.getMessage());
            return null;
        }
    }

    public static List<String> toList(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<String>>() {
            });
        } catch (JsonProcessingException e) {
            //log.error("Error while converting json to list", e.getMessage());
            return List.of();
        }
    }
}
