package io.github.imide.darkkore_reborn.config;

import com.electronwill.nightconfig.core.file.FileConfig;
import io.github.imide.darkkore_reborn.DarkKore_reborn;
import io.github.imide.darkkore_reborn.config.impl.FileObject;
import io.github.imide.darkkore_reborn.config.impl.JsonFileObject;
import io.github.imide.darkkore_reborn.config.impl.NightFileObject;
import io.github.imide.darkkore_reborn.config.options.Option;
import io.github.imide.darkkore_reborn.config.options.OptionHolder;
import io.github.imide.darkkore_reborn.gui.ConfigScreen;
import lombok.Getter;
import net.minecraft.client.gui.screen.Screen;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class ModConfig implements OptionHolder, ConfigHolder {

    @Getter
    protected FileObject config;

    public abstract File getFile();

    public void setupFileConfig() {
        if (!getFile().exists()) {
            try {
                getFile().getParentFile().mkdirs();
                getFile().createNewFile();
            } catch (IOException e) {
                DarkKore_reborn.LOGGER.error("Couldn't initialize config!", e);
            }
        }
        if (getFile().toString().endsWith(".toml")) {
            config = new NightFileObject(FileConfig.of(getFile()));
        } else {
            config = new JsonFileObject(getFile());
        }
    }

    @Override
    public void save() {
        setupFileConfig();
        config.load();
        for (Option<?> entry : getOptions()) {
            try {
                entry.save(config.getConfig());
            } catch (Exception e) {
                DarkKore_reborn.LOGGER.log(Level.WARN, "Fail saving option " + entry.getValue(), e);
            }
        }
        config.save();
        config.close();
    }

    public void rawLoad() {
        config.load();
        for (Option<?> entry : getOptions()) {
            try {
                entry.load(config.getConfig());
            } catch (Exception e) {
                DarkKore_reborn.LOGGER.log(Level.WARN, "Fail loading option " + entry.getValue(), e);
            }
        }
    }

    @Override
    public void load() {
        setupFileConfig();
        rawLoad();
        config.close();
    }

    public abstract List<Option<?>> getOptions();

    public Screen getScreen() {
        return  ConfigScreen.of(getOptions());
    }

}
