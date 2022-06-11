package io.github.darkkronicle.darkkore.settings;

import io.github.darkkronicle.darkkore.config.ModConfig;
import io.github.darkkronicle.darkkore.config.options.*;
import io.github.darkkronicle.darkkore.gui.config.ColorOptionComponent;
import io.github.darkkronicle.darkkore.hotkeys.HotkeySettings;
import io.github.darkkronicle.darkkore.hotkeys.HotkeySettingsOption;
import io.github.darkkronicle.darkkore.intialization.profiles.PlayerContextCheck;
import io.github.darkkronicle.darkkore.util.Color;
import io.github.darkkronicle.darkkore.util.FileUtil;
import org.lwjgl.glfw.GLFW;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DarkKoreConfig extends ModConfig {

    private final static DarkKoreConfig INSTANCE = new DarkKoreConfig();

    private final File file;

    public final ListOption<SoundType> soundType = new ListOption<>("soundType", "darkkore.option.soundtype", "darkkore.option.info.soundtype", SoundType.CHIME);

    public final HotkeySettingsOption openGui = new HotkeySettingsOption("openGui", "darkkore.option.opengui", "darkkore.option.info.opengui",
            new HotkeySettings(false, false, true, new ArrayList<>(List.of(GLFW.GLFW_KEY_J)), PlayerContextCheck.getDefault()));

    public final DoubleOption scrollScale = new DoubleOption("scrollScale", "darkkore.option.scrollscale", "darkkore.option.info.scrollscale", .3, 0.01, 10);

    public static DarkKoreConfig getInstance() {
        return INSTANCE;
    }

    private DarkKoreConfig() {
        file = new File(FileUtil.getConfigDirectory(), "darkkore.toml");
        setupFileConfig();
    }

    @Override
    public File getFile() {
        return file;
    }

    @Override
    public List<Option<?>> getOptions() {
        return List.of(soundType, openGui, scrollScale);
    }

    @Override
    public void addOption(Option<?> option) {

    }
}
