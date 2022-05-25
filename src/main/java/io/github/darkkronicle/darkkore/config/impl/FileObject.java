package io.github.darkkronicle.darkkore.config.impl;

public interface FileObject {

    void save();

    void load();

    void close();

    ConfigObject getConfig();

}
