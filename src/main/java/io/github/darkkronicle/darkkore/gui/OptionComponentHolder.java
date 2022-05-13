package io.github.darkkronicle.darkkore.gui;

import io.github.darkkronicle.darkkore.config.options.Option;
import io.github.darkkronicle.darkkore.gui.config.OptionComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OptionComponentHolder {

    private final static OptionComponentHolder INSTANCE = new OptionComponentHolder();

    public static OptionComponentHolder getInstance() {
        return INSTANCE;
    }

    private final List<OptionToComponent> converters = new ArrayList<>();

    private OptionComponentHolder() {

    }

    public void addConverter(OptionToComponent converter) {
        converters.add(converter);
    }

    public OptionComponent<?, ?> convert(Option<?> option, int width) {
        for (OptionToComponent converter : converters) {
            Optional<OptionComponent<?, ?>> converted = converter.getOption(option, width);
            if (converted.isPresent()) {
                return converted.get();
            }
        }
        return null;
    }

    public interface OptionToComponent {

        Optional<OptionComponent<?, ?>> getOption(Option<?> option, int width);

    }

}
