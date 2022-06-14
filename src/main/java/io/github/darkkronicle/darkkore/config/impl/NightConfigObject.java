package io.github.darkkronicle.darkkore.config.impl;

import com.electronwill.nightconfig.core.Config;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class NightConfigObject implements ConfigObject {

    @Getter protected Config object;

    @Override
    public <T> void set(String key, T value) {
        object.set(key, getElement(value));
    }

    @Override
    public <T> void set(String key, List<T> value) {
        object.set(key, value);
    }

    @Override
    public void set(String key, String value) {
        object.set(key, value);
    }

    @Override
    public void set(String key, boolean value) {
        object.set(key, value);
    }

    @Override
    public void set(String key, Number value) {
        object.set(key, value);
    }

    @Override
    public void set(String key, ConfigObject value) {
        object.set(key, getElement(value));
    }

    @Override
    public void remove(String key) {
        object.remove(key);
    }

    @Override
    public void clearAndCopy(ConfigObject object) {
        this.object.clear();
        for (Map.Entry<String, Object> entry : object.getValues().entrySet()) {
            set(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public NightConfigObject createNew() {
        return new NightConfigObject(object.createSubConfig());
    }

    @Override
    public <T> Optional<T> getOptional(String key) {
        return (Optional<T>) Optional.of(getAs(object.get(key)));
    }

    @Override
    public <T> T get(String key) {
        return (T) getAs(object.get(key));
    }

    @Override
    public boolean contains(String key) {
        return object.contains(key);
    }

    public Object getAs(Object object) {
        if (object instanceof Config) {
            return new NightConfigObject((Config) object);
        }
        return object;
    }

    public Object getElement(Object object) {
        if (object instanceof ConfigObject) {
            ConfigObject config = (ConfigObject) object;
            NightConfigObject nest = createNew();
            Config rawObj = nest.object;
            for (Map.Entry<String, Object> entry : config.getValues().entrySet()) {
                rawObj.set(entry.getKey(), getElement(entry.getValue()));
            }
            return rawObj;
        }
        return object;
    }

    @Override
    public Map<String, Object> getValues() {
        return object.valueMap();
    }

}
