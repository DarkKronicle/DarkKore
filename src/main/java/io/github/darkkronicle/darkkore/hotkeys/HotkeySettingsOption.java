package io.github.darkkronicle.darkkore.hotkeys;

import io.github.darkkronicle.darkkore.config.impl.ConfigObject;
import io.github.darkkronicle.darkkore.config.options.BasicOption;
import io.github.darkkronicle.darkkore.intialization.profiles.PlayerContextCheck;
import io.github.darkkronicle.darkkore.intialization.profiles.PlayerContextOption;
import io.github.darkkronicle.darkkore.util.InputUtil;
import org.lwjgl.glfw.GLFW;

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
        boolean blocking = nest.get("blocking");
        boolean ordered = nest.get("ordered");
        boolean exclusive = nest.get("exclusive");
        List<String> keys = nest.get("keys");
        PlayerContextCheck check = PlayerContextOption.load("check", nest);
        if (check == null) {
            check = getDefaultValue().getCheck().copy();
        }
        setValue(new HotkeySettings(exclusive, ordered, blocking, keys.stream().map(InputUtil::getKeyCode).toList(), check));
    }
}
