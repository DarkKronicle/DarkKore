package io.github.darkkronicle.darkkore.settings;

import io.github.darkkronicle.darkkore.colors.Colors;
import io.github.darkkronicle.darkkore.colors.ExtendedColor;
import io.github.darkkronicle.darkkore.config.ModConfig;
import io.github.darkkronicle.darkkore.config.options.*;
import io.github.darkkronicle.darkkore.gui.ConfigScreen;
import io.github.darkkronicle.darkkore.hotkeys.HotkeySettings;
import io.github.darkkronicle.darkkore.hotkeys.HotkeySettingsOption;
import io.github.darkkronicle.darkkore.intialization.profiles.PlayerContextCheck;
import io.github.darkkronicle.darkkore.util.Color;
import io.github.darkkronicle.darkkore.util.FileUtil;
import net.minecraft.client.gui.screen.Screen;
import org.lwjgl.glfw.GLFW;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DarkKoreConfig extends ModConfig {

    private final static DarkKoreConfig INSTANCE = new DarkKoreConfig();

    private final File file;

    public final ListOption<SoundType> soundType = new ListOption<>("soundType", "darkkore.option.soundtype", "darkkore.option.info.soundtype", SoundType.NORMAL);

    public final BooleanOption changeVanillaButtons = new BooleanOption("changeVanillaButtons", "darkkore.option.changevanillaoptions", "darkkore.option.info.changevanillaoptions",
            false);

    public final HotkeySettingsOption openGui = new HotkeySettingsOption("openGui", "darkkore.option.opengui", "darkkore.option.info.opengui",
            new HotkeySettings(false, false, true, new ArrayList<>(List.of(GLFW.GLFW_KEY_J)), PlayerContextCheck.getDefault()));

    public final OptionSection general = new OptionSection("general", "darkkore.option.section.general", "darkkore.option.section.info.general",
            List.of(soundType, changeVanillaButtons, openGui));

    public final DoubleOption scrollScale = new DoubleOption("scrollScale", "darkkore.option.scrollscale", "darkkore.option.info.scrollscale", .3, 0.01, 10);

    public final ExtendedColorOption hoverColor = new ExtendedColorOption("hoverColor", "darkkore.option.hovercolor", "darkkore.option.info.hovercolor",
            new ExtendedColor(new Color(0x64000000), ExtendedColor.ChromaOptions.getDefault()));

    public final ExtendedColorOption screenBackgroundColor = new ExtendedColorOption("screenBackgroundColor", "darkkore.option.screenbackgroundcolor", "darkkore.option.info.screenbackgroundcolor",
            new ExtendedColor(new Color(0xB0000000), ExtendedColor.ChromaOptions.getDefault()));

    public final OptionSection gui = new OptionSection("gui", "darkkore.option.section.gui", "darkkore.option.section.info.gui",
            List.of(scrollScale, hoverColor, screenBackgroundColor));

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
        return List.of(general, gui);
    }

    public List<OptionSection> getSections() {
        return List.of(general, gui);
    }

    @Override
    public Screen getScreen() {
        return ConfigScreen.ofSections(getSections());
    }
}
