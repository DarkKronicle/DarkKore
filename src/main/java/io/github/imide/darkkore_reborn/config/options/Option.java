package io.github.imide.darkkore_reborn.config.options;

import io.github.imide.darkkore_reborn.intialization.Saveable;

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
