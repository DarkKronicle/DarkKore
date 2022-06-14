package io.github.darkkronicle.darkkore.config.options;

import io.github.darkkronicle.darkkore.config.impl.ConfigObject;

import java.util.Optional;

public class ListOption<T extends OptionListEntry<T>> extends BasicOption<T> {

    public ListOption(String key, String displayName, String hoverName, T defaultValue) {
        super(key, displayName, hoverName, defaultValue);
    }

    @Override
    public void load(ConfigObject config) {
        if (!config.contains(key)) {
            setValue(getDefaultValue());
            return;
        }
        Optional<String> option = config.getOptional(key);
        if (option.isEmpty()) {
            setValue(getDefaultValue());
            return;
        }
        OptionListEntry<T> entry = getDefaultValue().fromString(option.get());
        if (entry != null) {
            setValue(getDefaultValue().fromString(option.get()));
        } else {
            setValue(getDefaultValue());
        }
    }

    @Override
    public void save(ConfigObject config) {
        config.set(getKey(), getValue().getSaveKey());
    }
}
