package io.github.darkkronicle.darkkore.settings;

import io.github.darkkronicle.darkkore.config.ModConfig;
import io.github.darkkronicle.darkkore.config.options.*;
import io.github.darkkronicle.darkkore.gui.config.DoubleOptionComponent;
import io.github.darkkronicle.darkkore.util.FileUtil;

import java.io.File;
import java.util.List;

public class DarkKoreConfig extends ModConfig {

    private final static DarkKoreConfig INSTANCE = new DarkKoreConfig();

    private final File file;

    public final BooleanOption debug = new BooleanOption("debug", "darkkore.option.debug", "darkkore.option.info.debug", false);

    public final BooleanOption cool = new BooleanOption("cool", "darkkore.option.cool", "darkkore.option.info.cool", false);

    public final ListOption<SoundType> soundType = new ListOption<>("soundType", "darkkore.option.soundtype", "darkkore.option.info.soundtype", SoundType.CHIME);

    public final StringOption stringOption = new StringOption("stringOption", "darkkore.option.stringoption", "darkkore.option.info.stringoption", "No!");

    public final IntegerOption integerOption = new IntegerOption("integerOption", "darkkore.option.integeroption", "darkkore.option.info.integeroption", 5);

    public final DoubleOption doubleOption = new DoubleOption("doubleOption", "darkkore.option.doubleoption", "darkkore.option.info.integeroption", 5.5, 0, 10);

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
        return List.of(debug, cool, soundType, stringOption, integerOption, doubleOption);
    }

    @Override
    public void addOption(Option<?> option) {

    }
}
