package io.github.darkkronicle.darkkore.config.options;

import io.github.darkkronicle.darkkore.DarkKore;
import io.github.darkkronicle.darkkore.config.impl.ConfigObject;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OptionSection extends BasicOption<List<Option<?>>> implements OptionHolder {

    public OptionSection(String key, String displayName, String hoverName) {
        this(key, displayName, hoverName, new ArrayList<>());
        this.setValue(new ArrayList<>());
    }

    public OptionSection(String key, String displayName, String hoverName, List<Option<?>> options) {
        super(key, displayName, hoverName, new ArrayList<>());
        this.setValue(options);
        this.setDefaultValue(new ArrayList<>(options));
    }

    @Override
    public List<Option<?>> getValue() {
        return value;
    }

    @Override
    public void addOption(Option<?> option) {
        value.add(option);
        this.setDefaultValue(new ArrayList<>(value));
    }

    @Override
    public List<Option<?>> getOptions() {
        return value;
    }

    @Override
    public void save(ConfigObject config) {
        Optional<ConfigObject> sub = config.getOptional(key);
        ConfigObject conf;
        if (sub == null || sub.isEmpty()) {
            conf = config.createNew();
            config.set(key, conf);
        } else {
            conf = sub.get();
        }
        for (Option<?> option : getOptions()) {
            try {
                option.save(conf);
            } catch (Exception e) {
                DarkKore.LOGGER.log(Level.WARN, "Fail saving option " + option.getValue(), e);
            }
        }
    }

    @Override
    public void load(ConfigObject config) {
        ConfigObject nest = config.get(key);
        if (nest == null) {
            return;
        }
        for (Option<?> option : getOptions()) {
            try {
                option.load(nest);
            } catch (Exception e) {
                DarkKore.LOGGER.log(Level.WARN, "Fail loading option " + option.getValue(), e);
            }
        }
    }
}
