package io.github.imide.darkkore_reborn.intialization;

import com.electronwill.nightconfig.core.Config;
import io.github.imide.darkkore_reborn.config.impl.ConfigObject;

/** A saveable object into a {@link Config} */
public interface Saveable {

    /**
     * Save the value into the configuration. Should put key then value.
     */
    void save(ConfigObject object);

    /**
     * Load the value from the configuration. Should fetch from key.
     */
    void load(ConfigObject object);

}
