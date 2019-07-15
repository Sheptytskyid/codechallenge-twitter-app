package com.codechallenge.twitterapp.cucumber.utils;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CucumberContext {

    public static final String REST_RESPONSE_ENTITY = "REST response";
    public static final String USERS_ENTITY = "Users";
    private final Map<String, Object> context = new HashMap<>();

    public void addEntity(String entityName, Object entity) {
        context.put(entityName, entity);
    }

    public <T> T getEntity(String entityName) {
        //noinspection unchecked
        return (T) context.get(entityName);
    }

    public void clear() {
        context.clear();
    }
}
