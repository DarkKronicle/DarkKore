package io.github.darkkronicle.darkkore.config.options;

import com.electronwill.nightconfig.core.Config;
import lombok.Getter;

import java.util.Optional;

public class BasicOption<T> implements Option<T> {

    @Getter
    protected final String key;

    protected final String displayNameKey;
    protected final String hoverNameKey;

    @Getter
    protected final T defaultValue;

    protected T value;

    public BasicOption(String key, String displayName, String hoverName, T defaultValue) {
        this.key = key;
        this.displayNameKey = displayName;
        this.hoverNameKey = hoverName;
        this.defaultValue = defaultValue;
    }

    @Override
    public T getValue() {
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    @Override
    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public void save(Config config) {
        config.set(key, value);
    }

    @Override
    public void load(Config config) {
        if (!config.contains(key)) {
            value = defaultValue;
            return;
        }
        Optional<T> option = config.getOptional(key);
        if (option.isEmpty()) {
            value = defaultValue;
            return;
        }
        value = option.get();
    }

    @Override
    public String getNameKey() {
        return displayNameKey;
    }

    @Override
    public String getInfoKey() {
        return hoverNameKey;
    }
}
