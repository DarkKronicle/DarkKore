package io.github.darkkronicle.darkkore.intialization;

import com.electronwill.nightconfig.core.Config;

/** A saveable object into a {@link Config} */
public interface Saveable {

    /**
     * Save the value into the configuration. Should put key then value.
     * @param config {@link Config} to save into
     */
    void save(Config config);

    /**
     * Load the value from the configuration. Should fetch from key.
     * @param config {@link Config} to load from
     */
    void load(Config config);

}
