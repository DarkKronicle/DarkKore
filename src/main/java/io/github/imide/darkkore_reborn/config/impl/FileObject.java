package io.github.imide.darkkore_reborn.config.impl;

public interface FileObject {

    void save();

    void load();

    void close();

    ConfigObject getConfig();

}
