package io.github.darkkronicle.darkkore.config.options;

import io.github.darkkronicle.darkkore.intialization.Saveable;

public interface Option<T> extends Saveable {

    T getValue();

    T getDefaultValue();

    void setValue(T value);

    String getNameKey();

    String getInfoKey();

    default boolean isDefault() {
        return getValue().equals(getDefaultValue());
    }

}
