package io.github.darkkronicle.darkkore.config.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

@AllArgsConstructor
public class JsonConfigObject implements ConfigObject {

    @Getter private JsonObject object;

    public JsonConfigObject() {
        this(new JsonObject());
    }

    @Override
    public <T> void set(String key, T value) {
        JsonElement element = getElement(value);
        if (element == null) {
            throw new IllegalArgumentException("Not a correct type for JSON!");
        }
        object.add(key, element);
    }

    public JsonElement getElement(Object value) {
        if (value instanceof String) {
            return new JsonPrimitive((String) value);
        } else if (value instanceof Number) {
            return new JsonPrimitive((Number) value);
        } else if (value instanceof Boolean) {
            return new JsonPrimitive((Boolean) value);
        } else if (value instanceof ConfigObject) {
            JsonConfigObject nest = new JsonConfigObject();
            nest.clearAndCopy((ConfigObject) value);
            return nest.getObject();
        } else if (value instanceof List<?>) {
            JsonArray array = new JsonArray();
            for (Object v : (List) value) {
                array.add(getElement(v));
            }
            return array;
        }
        return null;
    }

    public Object getAs(JsonElement element) {
        if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = (JsonPrimitive) element;
            if (primitive.isBoolean()) {
                return primitive.getAsBoolean();
            }
            if (primitive.isNumber()) {
                return primitive.getAsNumber().doubleValue();
            }
            if (primitive.isString()) {
                return primitive.getAsString();
            }
            return primitive.getAsString();
        }
        if (element.isJsonArray()) {
            List<Object> obj = new ArrayList<>();
            for (JsonElement el : element.getAsJsonArray()) {
                obj.add(getAs(el));
            }
            return obj;
        }
        if (element.isJsonObject()) {
            return new JsonConfigObject(element.getAsJsonObject());
        }
        return null;
    }

    @Override
    public <T> void set(String key, List<T> value) {
        JsonArray array = new JsonArray();
        for (T v : value) {
            array.add(getElement(v));
        }
        object.add(key, array);
    }

    @Override
    public void set(String key, String value) {
        object.add(key, new JsonPrimitive(value));
    }

    @Override
    public void set(String key, boolean value) {
        object.add(key, new JsonPrimitive(value));
    }

    @Override
    public void set(String key, Number value) {
        object.add(key, new JsonPrimitive(value));
    }

    @Override
    public void set(String key, ConfigObject value) {
        object.add(key, getElement(value));
    }

    @Override
    public void remove(String key) {
        object.remove(key);
    }

    @Override
    public void clearAndCopy(ConfigObject object) {
        this.object = new JsonObject();
        for (Map.Entry<String, Object> entry : object.getValues().entrySet()) {
            set(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public ConfigObject createNew() {
        return new JsonConfigObject();
    }

    @Override
    public <T> Optional<T> getOptional(String key) {
        if (!contains(key)) {
            return Optional.empty();
        }
        return Optional.of((T) getAs(object.get(key)));
    }

    @Override
    public <T> T get(String key) {
        return (T) getAs(object.get(key));
    }

    @Override
    public boolean contains(String key) {
        return object.has(key);
    }

    @Override
    public Map<String, Object> getValues() {
        Map<String, Object> value = new HashMap<>();
        for (Map.Entry<String, JsonElement> element : object.entrySet()) {
            value.put(element.getKey(), getAs(element.getValue()));
        }
        return value;
    }
}
