package tp.market.util;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

public class MyJsonUtil {
    private static ObjectMapper mapper = new ObjectMapper();

    public static String toJsonString(Object object) {
        return  mapper.writeValueAsString(object);
    }

    public static <T> T fromJsonString(String jsonString,Class<T> c) {
        return mapper.readValue(jsonString, c);
    }
    public static <T> T fromJsonString(String jsonString, TypeReference<T> tr) {
        return mapper.readValue(jsonString, tr);
    }
}
