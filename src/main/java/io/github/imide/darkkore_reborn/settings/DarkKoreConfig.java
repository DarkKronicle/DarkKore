package io.github.imide.darkkore_reborn.settings;

import io.github.imide.darkkore_reborn.colors.Colors;
import io.github.imide.darkkore_reborn.colors.ExtendedColor;
import io.github.imide.darkkore_reborn.config.ModConfig;
import io.github.imide.darkkore_reborn.config.options.*;
import io.github.imide.darkkore_reborn.gui.ConfigScreen;
import io.github.imide.darkkore_reborn.hotkeys.HotkeySettings;
import io.github.imide.darkkore_reborn.hotkeys.HotkeySettingsOption;
import io.github.imide.darkkore_reborn.intialization.profiles.PlayerContextCheck;
import io.github.imide.darkkore_reborn.util.Color;
import io.github.imide.darkkore_reborn.util.FileUtil;
import net.minecraft.client.gui.screen.Screen;
import org.lwjgl.glfw.GLFW;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DarkKoreConfig extends ModConfig {

    private final static DarkKoreConfig INSTANCE = new DarkKoreConfig();

    private final File file;

    public final ListOption<SoundType> soundType = new ListOption<>("soundType", "darkkore-reborn.option.soundtype", "darkkore-reborn.option.info.soundtype", SoundType.NORMAL);

    public final BooleanOption changeVanillaButtons = new BooleanOption("changeVanillaButtons", "darkkore-reborn.option.changevanillaoptions", "darkkore-reborn.option.info.changevanillaoptions",
            false);

    public final HotkeySettingsOption openGui = new HotkeySettingsOption("openGui", "darkkore-reborn.option.opengui", "darkkore-reborn.option.info.opengui",
            new HotkeySettings(false, false, true, new ArrayList<>(List.of(GLFW.GLFW_KEY_J)), PlayerContextCheck.getDefault()));

    public final OptionSection general = new OptionSection("general", "darkkore-reborn.option.section.general", "darkkore-reborn.option.section.info.general",
            List.of(soundType, changeVanillaButtons, openGui));

    public final DoubleOption scrollScale = new DoubleOption("scrollScale", "darkkore-reborn.option.scrollscale", "darkkore-reborn.option.info.scrollscale", .3, 0.01, 10);

    public final ExtendedColorOption hoverColor = new ExtendedColorOption("hoverColor", "darkkore-reborn.option.hovercolor", "darkkore-reborn.option.info.hovercolor",
            new ExtendedColor(new Color(0x64000000), ExtendedColor.ChromaOptions.getDefault()));

    public final ExtendedColorOption screenBackgroundColor = new ExtendedColorOption("screenBackgroundColor", "darkkore-reborn.option.screenbackgroundcolor", "darkkore-reborn.option.info.screenbackgroundcolor",
            new ExtendedColor(new Color(0xB0000000), ExtendedColor.ChromaOptions.getDefault()));

    public final OptionSection gui = new OptionSection("gui", "darkkore-reborn.option.section.gui", "darkkore-reborn.option.section.info.gui",
            List.of(scrollScale, hoverColor, screenBackgroundColor));

    public static DarkKoreConfig getInstance() {
        return INSTANCE;
    }

    private DarkKoreConfig() {
        file = new File(FileUtil.getConfigDirectory(), "darkkore-reborn.toml");
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
