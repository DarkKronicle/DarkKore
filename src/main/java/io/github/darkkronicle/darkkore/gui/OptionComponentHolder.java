package io.github.darkkronicle.darkkore.gui;

import io.github.darkkronicle.darkkore.config.options.Option;
import io.github.darkkronicle.darkkore.gui.config.OptionComponent;
import net.minecraft.client.gui.screen.Screen;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
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
        Collections.sort(converters);
    }

    public OptionComponent<?, ?> convert(Screen parent, Option<?> option, int width) {
        for (OptionToComponent converter : converters) {
            Optional<OptionComponent<?, ?>> converted = converter.getOption(parent, option, width);
            if (converted.isPresent()) {
                return converted.get();
            }
        }
        return null;
    }

    public void addWithOrder(Integer order, OptionToComponent component) {
        addConverter(createOption(order, component));
    }

    public static OptionToComponent createOption(Integer order, OptionToComponent component) {
        return new OptionToComponent() {
            @Override
            public Optional<OptionComponent<?, ?>> getOption(Screen parent, Option<?> option, int width) {
                return component.getOption(parent, option, width);
            }

            @Override
            public Integer order() {
                return order;
            }
        };
    }

    public interface OptionToComponent extends Comparable<OptionToComponent> {

        Optional<OptionComponent<?, ?>> getOption(Screen parent, Option<?> option, int width);

        default Integer order() {
            return 0;
        }

        @Override
        default int compareTo(@NotNull OptionComponentHolder.OptionToComponent o) {
            return order().compareTo(o.order());
        }
    }

}
