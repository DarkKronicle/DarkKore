package io.github.darkkronicle.darkkore.intialization;

import com.electronwill.nightconfig.core.Config;
import io.github.darkkronicle.darkkore.config.impl.ConfigObject;

/** A saveable object into a {@link Config} */
public interface Saveable {

    /**
     * Save the value into the configuration. Should put key then value.
     */
    void save(ConfigObject object);

    /**
     * Load the value from the configuration. Should fetch from key.
     * @param config {@link Config} to load from
     */
    void load(ConfigObject object);

}
