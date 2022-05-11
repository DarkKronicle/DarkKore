package io.github.darkkronicle.darkkore.intialization;

import com.electronwill.nightconfig.core.Config;

public interface Saveable {

    void save(Config config);

    void load(Config config);

}
