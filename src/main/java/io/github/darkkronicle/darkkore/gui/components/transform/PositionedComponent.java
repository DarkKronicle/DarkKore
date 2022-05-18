package io.github.darkkronicle.darkkore.gui.components.transform;

import io.github.darkkronicle.darkkore.gui.components.Component;

public class PositionedComponent extends OffsetComponent {

    private int x;
    private int y;

    public PositionedComponent(Component component, int x, int y, int width, int height) {
        super(component, width, height);
        this.x = x;
        this.y = y;
    }

    @Override
    public int getXOffset() {
        return x;
    }

    @Override
    public int getYOffset() {
        return y;
    }
}
