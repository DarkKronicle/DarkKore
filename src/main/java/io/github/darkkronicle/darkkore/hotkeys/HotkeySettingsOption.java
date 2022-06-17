package io.github.darkkronicle.darkkore.hotkeys;

import io.github.darkkronicle.darkkore.config.impl.ConfigObject;
import io.github.darkkronicle.darkkore.config.options.BasicOption;
import io.github.darkkronicle.darkkore.intialization.profiles.PlayerContextCheck;
import io.github.darkkronicle.darkkore.intialization.profiles.PlayerContextOption;
import io.github.darkkronicle.darkkore.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HotkeySettingsOption extends BasicOption<HotkeySettings> {

    public HotkeySettingsOption(String key, String displayName, String hoverName, HotkeySettings defaultValue) {
        super(key, displayName, hoverName, defaultValue);
    }

    @Override
    public void save(ConfigObject config) {
        ConfigObject nest = config.createNew();
        HotkeySettings settings = getValue();
        nest.set("blocking", settings.isBlocking());
        nest.set("ordered", settings.isOrdered());
        nest.set("exclusive", settings.isExclusive());
        nest.set("keys", settings.getKeys().stream().map(num -> InputUtil.getKeyName(num).toUpperCase(Locale.ROOT)).toList());
        PlayerContextOption.save("check", settings.getCheck(), nest);
        config.set(key, nest);
    }

    @Override
    public void load(ConfigObject config) {
        ConfigObject nest = config.get(key);
        if (nest == null) {
            setValue(getDefaultValue());
            return;
        }
        boolean blocking = (boolean) nest.getOptional("blocking").orElseGet(() -> getDefaultValue().isBlocking());
        boolean ordered = (boolean) nest.getOptional("ordered").orElseGet(() -> getDefaultValue().isOrdered());
        boolean exclusive = (boolean) nest.getOptional("exclusive").orElseGet(() -> getDefaultValue().isExclusive());
        List<String> keys = (List<String>) nest.getOptional("keys").orElseGet(ArrayList::new);
        PlayerContextCheck check = PlayerContextOption.load("check", nest);
        if (check == null) {
            check = getDefaultValue().getCheck().copy();
        }
        setValue(new HotkeySettings(exclusive, ordered, blocking, keys.stream().map(InputUtil::getKeyCode).toList(), check));
    }
}
