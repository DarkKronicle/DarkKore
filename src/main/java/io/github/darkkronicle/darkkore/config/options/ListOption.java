package io.github.darkkronicle.darkkore.config.options;

import com.electronwill.nightconfig.core.Config;

import java.util.Optional;

public class ListOption<T extends OptionListEntry<T>> extends BasicOption<T> {

    public ListOption(String key, String displayName, String hoverName, T defaultValue) {
        super(key, displayName, hoverName, defaultValue);
    }

    @Override
    public void load(Config config) {
        if (!config.contains(key)) {
            value = defaultValue;
            return;
        }
        Optional<String> option = config.getOptional(key);
        if (option.isEmpty()) {
            value = defaultValue;
            return;
        }
        OptionListEntry<T> entry = defaultValue.fromString(option.get());
        if (entry != null) {
            value = defaultValue.fromString(option.get());
        } else {
            value = defaultValue;
        }
    }

    @Override
    public void save(Config config) {
        config.set(getKey(), value.getSaveKey());
    }
}
