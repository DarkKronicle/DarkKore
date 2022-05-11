package io.github.darkkronicle.darkkore.config;

import com.electronwill.nightconfig.core.file.FileConfig;
import io.github.darkkronicle.darkkore.DarkKore;
import io.github.darkkronicle.darkkore.config.options.Option;
import io.github.darkkronicle.darkkore.config.options.OptionHolder;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class ModConfig implements OptionHolder, ConfigHolder {

    @Getter
    protected FileConfig config;

    public abstract File getFile();

    public void setupFileConfig() {
        if (!getFile().exists()) {
            try {
                getFile().createNewFile();
            } catch (IOException e) {
                DarkKore.LOGGER.error("Couldn't initialize config!", e);
            }
        }
        config = FileConfig.of(getFile());
    }

    public void save() {
        setupFileConfig();
        config.load();
        for (Option<?> entry : getOptions()) {
            entry.save(config);
        }
        config.save();
        config.close();
    }

    public void rawLoad() {
        config.load();
        for (Option<?> entry : getOptions()) {
            entry.load(config);
        }
    }

    public void load() {
        setupFileConfig();
        rawLoad();
        config.close();
    }

    public abstract List<Option<?>> getOptions();

}
