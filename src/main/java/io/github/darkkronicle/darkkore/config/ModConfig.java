package io.github.darkkronicle.darkkore.config;

import com.electronwill.nightconfig.core.file.FileConfig;
import io.github.darkkronicle.darkkore.config.options.Option;
import io.github.darkkronicle.darkkore.config.options.OptionHolder;
import lombok.Getter;

import java.io.File;
import java.util.List;

public abstract class ModConfig implements OptionHolder, ConfigHolder {

    @Getter
    protected FileConfig config;

    public abstract File getFile();

    public void setupFileConfig() {
        config = FileConfig.of(getFile());
    }

    public void save() {
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
        rawLoad();
        config.close();
    }

    public abstract List<Option<?>> getOptions();

}
