package io.github.darkkronicle.darkkore.config;

import io.github.darkkronicle.darkkore.config.options.BooleanOption;
import io.github.darkkronicle.darkkore.config.options.Option;
import io.github.darkkronicle.darkkore.util.FileUtil;
import net.minecraft.client.MinecraftClient;

import java.io.File;
import java.util.List;

public class DarkKoreConfig extends ModConfig {

    private final static DarkKoreConfig INSTANCE = new DarkKoreConfig();

    private final File file;

    public final BooleanOption debug = new BooleanOption("debug", "darkkore.option.debug", "darkkore.option.info.debug", false);

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
        return List.of(debug);
    }

    @Override
    public void addOption(Option<?> option) {

    }
}
