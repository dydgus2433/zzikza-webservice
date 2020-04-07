package com.zzikza.springboot.web.util;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.json.JsonParseException;

import java.io.IOException;
import java.util.Map;

public class JsonUtil {

    public static Map<String, Object> jsonStringToMap(String result) throws IOException, JsonParseException, JsonMappingException {
        ObjectMapper mapper = new ObjectMapper();
        Map map = mapper.readValue(result, Map.class);
        return map;
    }
}
