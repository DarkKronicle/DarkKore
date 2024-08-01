package io.github.imide.darkkore_reborn.config.options;

import io.github.imide.darkkore_reborn.colors.ColorAlias;
import io.github.imide.darkkore_reborn.config.impl.ConfigObject;
import io.github.imide.darkkore_reborn.util.Color;
import io.github.imide.darkkore_reborn.util.ColorUtil;

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
        setValue(parseString(option.get()));
    }

    public static ColorAlias parseString(String option) {
        if (option.startsWith("#")) {
            return new ColorAlias(ColorUtil.getColorFromString(option));
        }
        return new ColorAlias(option);
    }

}
