package io.github.darkkronicle.darkkore.config.options;

import io.github.darkkronicle.darkkore.config.impl.ConfigObject;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

public class BasicOption<T> implements Option<T> {

    @Getter
    protected final String key;

    protected final String displayNameKey;
    protected final String hoverNameKey;

    @Getter
    @Setter
    protected T defaultValue;

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
            return getDefaultValue();
        }
        return value;
    }

    @Override
    public void setValue(T value) {
        if (value == null) {
            this.value = getDefaultValue();
        } else {
            this.value = value;
        }
    }

    @Override
    public void save(ConfigObject config) {
        config.set(key, getValue());
    }

    @Override
    public void load(ConfigObject config) {
        if (!config.contains(key)) {
            setValue(getDefaultValue());
            return;
        }
        Optional<T> option = config.getOptional(key);
        if (option.isEmpty()) {
            setValue(getDefaultValue());
            return;
        }
        setValue(option.get());
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
