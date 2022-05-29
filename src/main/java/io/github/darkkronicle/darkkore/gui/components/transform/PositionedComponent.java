package io.github.darkkronicle.darkkore.gui.components.transform;

import io.github.darkkronicle.darkkore.gui.components.Component;
import net.minecraft.client.gui.screen.Screen;

public class PositionedComponent extends OffsetComponent {

    private int x;
    private int y;

    public PositionedComponent(Screen parent, Component component, int x, int y, int width, int height) {
        super(parent, component, width, height);
        this.x = x;
        this.y = y;
    }

    public PositionedComponent(Screen parent, Component component, int x, int y) {
        this(parent, component, x, y, -1, -1);
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
