package io.github.darkkronicle.darkkore.config.options;

import io.github.darkkronicle.darkkore.colors.ColorAlias;
import io.github.darkkronicle.darkkore.config.impl.ConfigObject;
import io.github.darkkronicle.darkkore.util.Color;
import io.github.darkkronicle.darkkore.util.ColorUtil;

import java.util.Optional;

public class ColorOption extends BasicOption<ColorAlias> {

    public ColorOption(String key, String displayName, String hoverName, Color defaultValue) {
        this(key, displayName, hoverName, new ColorAlias(defaultValue));
    }

    public ColorOption(String key, String displayName, String hoverName, ColorAlias defaultValue) {
        super(key, displayName, hoverName, defaultValue);
    }

    @Override
    public void save(ConfigObject config) {
        String color = getValue().isAlias() ? getValue().getAliasName() : getValue().getString();
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
        if (option.get().startsWith("#")) {
            setValue(new ColorAlias(ColorUtil.getColorFromString(option.get())));
            return;
        }
        setValue(new ColorAlias(option.get()));
    }

}
