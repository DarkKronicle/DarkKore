package io.github.darkkronicle.darkkore.config.impl;

import com.electronwill.nightconfig.core.file.FileConfig;

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
        config.load();
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
