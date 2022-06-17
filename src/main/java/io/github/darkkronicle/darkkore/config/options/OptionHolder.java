package io.github.darkkronicle.darkkore.config.options;

import java.util.List;

public interface OptionHolder {

    List<Option<?>> getOptions();

    @Deprecated
    default void addOption(Option<?> option) {

    }

}
