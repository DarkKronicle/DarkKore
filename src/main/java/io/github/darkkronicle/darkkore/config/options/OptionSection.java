package io.github.darkkronicle.darkkore.config.options;

import com.electronwill.nightconfig.core.Config;
import io.github.darkkronicle.darkkore.config.ConfigHolder;
import io.github.darkkronicle.darkkore.config.impl.ConfigObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OptionSection extends BasicOption<List<Option<?>>> implements OptionHolder {

    private final List<Option<?>> options = new ArrayList<>();

    public OptionSection(String key, String displayName, String hoverName) {
        super(key, displayName, hoverName, new ArrayList<>());
    }

    @Override
    public List<Option<?>> getValue() {
        return options;
    }

    @Override
    public void addOption(Option<?> option) {
        options.add(option);
    }

    @Override
    public List<Option<?>> getOptions() {
        return value;
    }

    @Override
    public void save(ConfigObject config) {
        Optional<ConfigObject> sub = config.get(key);
        ConfigObject conf;
        if (sub.isEmpty()) {
            conf = config.createNew();
            config.set(key, conf);
        } else {
            conf = sub.get();
        }
        for (Option<?> option : getOptions()) {
            option.save(conf);
        }
    }

    @Override
    public void load(ConfigObject config) {
        ConfigObject nest = config.get(key);
        if (nest == null) {
            return;
        }
        for (Option<?> option : getOptions()) {
            option.load(nest);
        }
    }
}
