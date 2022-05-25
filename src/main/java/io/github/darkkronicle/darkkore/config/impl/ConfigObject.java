package io.github.darkkronicle.darkkore.config.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ConfigObject {

    <T> void set(String key, T value);

    <T> void set(String key, List<T> value);

    void set(String key, String value);

    void set(String key, boolean value);

    void set(String key, Number value);

    void set(String key, ConfigObject value);

    void remove(String key);

    void clearAndCopy(ConfigObject object);

    ConfigObject createNew();

    <T> Optional<T> getOptional(String key);

    <T> T get(String key);

    boolean contains(String key);

    Map<String, Object> getValues();

}
