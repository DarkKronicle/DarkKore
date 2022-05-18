package io.github.darkkronicle.darkkore.intialization;

import io.github.darkkronicle.darkkore.DarkKore;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A class to help load necessary components of mods after the client has initialized.
 * <p>It sorts off of a priority and then if any are the same it sorts via their name. With this
 * {@link Initializer}s should always load in the same order.
 */
public class InitializationHandler {

    private final static InitializationHandler INSTANCE = new InitializationHandler();

    private boolean initialized = false;

    public static InitializationHandler getInstance() {
        return INSTANCE;
    }

    private List<LoadOrder> toLoad = new ArrayList<>();

    private InitializationHandler() {}

    /**
     * Adds an initializer to be called when client has been loaded
     * @param name Name of initializer. Typically the mod id
     * @param priority Priority of the initializer. Sorts from least to greatest
     * @param handler {@link Initializer} to call
     */
    public void registerInitializer(String name, int priority, Initializer handler) {
        toLoad.add(new LoadOrder(name, priority, handler));
    }

    /** Do not call */
    public void load() {
        if (initialized) {
            // We only ever want this once
            DarkKore.LOGGER.error("Attempting to initialize again! This should not be happening!");
            return;
        }
        Collections.sort(toLoad);
        for (LoadOrder load : toLoad) {
            load.getHandler().init();
        }
        toLoad = null;
        initialized = true;
    }

    /**
     * A class holding data for a mod to be initialized
     */
    @AllArgsConstructor
    @Value
    public static class LoadOrder implements Comparable<LoadOrder> {

        /*** Name of the loader */
        String name;

        /** Order value */
        Integer order;

        /** The initializer */
        Initializer handler;

        @Override
        public int compareTo(LoadOrder o) {
            // Sort number
            int compared = order.compareTo(o.order);
            if (compared == 0) {
                // Number not sorted so check name
                return name.compareTo(o.getName());
            }
            return compared;
        }
    }

}
