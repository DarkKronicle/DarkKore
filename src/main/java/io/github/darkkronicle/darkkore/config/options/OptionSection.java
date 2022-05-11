package io.github.darkkronicle.darkkore.config.options;

import com.electronwill.nightconfig.core.Config;
import io.github.darkkronicle.darkkore.config.options.BasicOption;
import io.github.darkkronicle.darkkore.config.options.Option;
import io.github.darkkronicle.darkkore.config.options.OptionHolder;

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
    public void save(Config config) {
        Optional<Config> sub = config.getOptional(key);
        Config conf;
        if (sub.isEmpty()) {
            conf = config.createSubConfig();
            config.add(key, conf);
        } else {
            conf = sub.get();
        }
        for (Option<?> option : getOptions()) {
            option.save(conf);
        }
    }

    @Override
    public void load(Config config) {

    }
}
