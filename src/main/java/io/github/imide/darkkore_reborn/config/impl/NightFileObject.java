package io.github.imide.darkkore_reborn.config.impl;

import com.electronwill.nightconfig.core.file.FileConfig;
import com.electronwill.nightconfig.core.io.WritingException;
import io.github.imide.darkkore_reborn.DarkKore_reborn;

import java.io.IOException;

public class NightFileObject extends NightConfigObject implements FileObject {

    private final FileConfig config;

    public NightFileObject(FileConfig config) {
        super(config);
        this.config = config;
    }

    @Override
    public void save() {
        config.save();
    }

    @Override
    public void load() {
        try {
            config.load();
        } catch (WritingException e) {
            config.getFile().getParentFile().mkdirs();
            try {
                config.getFile().createNewFile();
                config.load();
            } catch (IOException s) {
                DarkKore_reborn.LOGGER.error("Couldn't set up file" + config.getFile());
            }
        }
    }

    @Override
    public void close() {
        config.close();
    }

    @Override
    public ConfigObject getConfig() {
        return this;
    }

}
