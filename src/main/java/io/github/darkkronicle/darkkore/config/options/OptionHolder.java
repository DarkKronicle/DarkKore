package io.github.darkkronicle.darkkore.config.options;

import java.util.List;

public interface OptionHolder {

    List<Option<?>> getOptions();

    void addOption(Option<?> option);

}
