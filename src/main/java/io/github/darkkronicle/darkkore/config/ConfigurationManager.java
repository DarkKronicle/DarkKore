package io.github.darkkronicle.darkkore.config;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationManager implements ConfigHolder {

    private final static ConfigurationManager INSTANCE = new ConfigurationManager();

    private final List<ConfigHolder> configs = new ArrayList<>();

    public static ConfigurationManager getInstance() {
        return INSTANCE;
    }

    private ConfigurationManager() {}

    public void add(ConfigHolder config) {
        configs.add(config);
    }

    @Override
    public void save() {
        for (ConfigHolder config : configs) {
            config.save();
        }
    }

    @Override
    public void load() {
        for (ConfigHolder config : configs) {
            config.load();
        }
    }

}
