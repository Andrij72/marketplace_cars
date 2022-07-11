package com.andr.helper.serialize;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.andr.model.City;

import java.lang.reflect.Type;

public class CitySerializer implements JsonSerializer<City> {
    @Override
    public JsonElement serialize(
            City city, Type type, JsonSerializationContext context
    ) {
        JsonObject result = new JsonObject();
        result.addProperty("id", city.getId());
        result.addProperty("name", city.getName());
        return result;
    }
}