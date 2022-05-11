package io.github.darkkronicle.darkkore.config.options;

import io.github.darkkronicle.darkkore.intialization.Saveable;

public interface Option<T> extends Saveable {

    T getValue();

    void setValue(T value);

}
