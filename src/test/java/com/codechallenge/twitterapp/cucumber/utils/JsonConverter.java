package com.codechallenge.twitterapp.cucumber.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;

public class JsonConverter {

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());
    ;

    public static String toJson(Object object) throws IOException {
        return MAPPER.writeValueAsString(object);
    }

    public static final <T> T fromJson(String json, Class<T> aClass) throws IOException {
        return MAPPER.readValue(json, aClass);
    }

}
