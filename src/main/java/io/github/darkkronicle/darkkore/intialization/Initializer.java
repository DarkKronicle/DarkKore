package io.github.darkkronicle.darkkore.intialization;

/**
 * A class to be called when something needs to be loaded/setup. This is mostly used in {@link InitializationHandler}
 */
public interface Initializer {

    /** Called when it should be setup */
    void init();

}
