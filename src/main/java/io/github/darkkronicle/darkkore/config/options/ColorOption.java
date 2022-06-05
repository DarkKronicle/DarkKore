package io.github.darkkronicle.darkkore.config.options;

import io.github.darkkronicle.darkkore.config.impl.ConfigObject;
import io.github.darkkronicle.darkkore.util.Color;
import io.github.darkkronicle.darkkore.util.ColorUtil;

import java.util.Optional;

public class ColorOption extends BasicOption<Color> {

    public ColorOption(String key, String displayName, String hoverName, Color defaultValue) {
        super(key, displayName, hoverName, defaultValue);
    }

    @Override
    public void save(ConfigObject config) {
        String color = getValue().getString();
        config.set(key, color);
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
        setValue(ColorUtil.getColorFromString(option.get()));
    }

}
