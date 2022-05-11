package io.github.darkkronicle.darkkore.intialization;

import io.github.darkkronicle.darkkore.DarkKore;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InitializationHandler {

    private final static InitializationHandler INSTANCE = new InitializationHandler();

    private boolean initialized = false;

    public static InitializationHandler getInstance() {
        return INSTANCE;
    }

    private List<LoadOrder> toLoad = new ArrayList<>();

    private InitializationHandler() {}

    public void registerInitializer(String name, int priority, Initializer handler) {
        toLoad.add(new LoadOrder(name, priority, handler));
    }

    /** Do not call */
    public void load() {
        if (initialized) {
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

    @AllArgsConstructor
    @Value
    public static class LoadOrder implements Comparable<LoadOrder> {
        String name;
        Integer order;
        Initializer handler;

        @Override
        public int compareTo(LoadOrder o) {
            int compared = order.compareTo(o.order);
            if (compared == 0) {
                return name.compareTo(o.getName());
            }
            return compared;
        }
    }

}
