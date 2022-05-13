package io.github.darkkronicle.darkkore.gui.config;

import io.github.darkkronicle.darkkore.config.options.Option;
import io.github.darkkronicle.darkkore.gui.components.MultiComponent;
import io.github.darkkronicle.darkkore.util.Dimensions;
import lombok.Getter;

public abstract class OptionComponent<N, T extends Option<N>> extends MultiComponent {

    @Getter
    protected final T option;

    public OptionComponent(T option, int width, int height) {
        super(width, height);
        this.option = option;
        addComponents(new Dimensions(getBoundingBox()));
    }

    public abstract void addComponents(Dimensions bounds);

}
