package io.github.darkkronicle.darkkore.gui.components.transform;

import io.github.darkkronicle.darkkore.gui.components.Component;
import io.github.darkkronicle.darkkore.util.PositionedRectangle;
import io.github.darkkronicle.darkkore.util.Rectangle;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;

/**
 * A {@link WrapperComponent} that offsets a {@link Component} by a certain x/y value. This class is abstract
 * to allow for dynamic changing of position, such as {@link ScrollComponent}
 *
 * <p>This class essentially shifts everything over when events happen
 */
public abstract class OffsetComponent extends WrapperComponent {

    public OffsetComponent(Screen parent, Component component, int width, int height) {
        super(parent, component, width, height);
    }

    /**
     * Gets the current x position that the component should be at.
     *
     * <p>This isn't the <i>true</i> x position, just relative to the parent component
     * @return X position
     */
    public abstract int getXOffset();

    /**
     * Gets the current y position that the component should be at.
     *
     * <p>This isn't the <i>true</i> y position, just relative to the parent component
     * @return Y position
     */
    public abstract int getYOffset();

    /** {@inheritDoc} */
    @Override
    public void render(DrawContext context, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY) {
        int offX = this.getXOffset();
        int offY = this.getYOffset();
        super.render(context, getRenderBounds(x, y), x + offX, y + offY, mouseX, mouseY);
    }

    /**
     * Get the boundaries for rendering. This is its own method since some cases may want to change this
     * @param x X value for rendering
     * @param y Y value for rendering
     * @return A {@link PositionedRectangle} with necessary data
     */
    protected PositionedRectangle getRenderBounds(int x, int y) {
        Rectangle bound = getBoundingBox();
        int offX = this.getXOffset();
        int offY = this.getYOffset();
        return new PositionedRectangle(x + offX, y + offY, bound.width(), bound.height());
    }

    /** {@inheritDoc} */
    @Override
    public void postRender(DrawContext context, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY) {
        if (component.shouldPostRender()) {
            int offX = this.getXOffset();
            int offY = this.getYOffset();
            component.postRender(context, renderBounds, x + offX, y + offY, mouseX, mouseY);
        }
    }

    /** {@inheritDoc} */
    @Override
    public Rectangle getBoundingBox() {
        Rectangle bound = component.getBoundingBox();
        return new Rectangle(useComponentWidth ? bound.width() : width, useComponentHeight ? bound.height() : height);
    }

    /** {@inheritDoc} */
    @Override
    public boolean mouseScrolled(int x, int y, int mouseX, int mouseY, double amount) {
        if (isHovered()) {
            int offX = this.getXOffset();
            int offY = this.getYOffset();
            return component.mouseScrolled(x + offX, y + offY, mouseX, mouseY, amount);
        }
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public void mouseClickedOutsideImpl(int x, int y, int mouseX, int mouseY, int button) {
        int offX = this.getXOffset();
        int offY = this.getYOffset();
        component.mouseClickedOutside(x + offX, y + offY, mouseX, mouseY, button);
    }

}
