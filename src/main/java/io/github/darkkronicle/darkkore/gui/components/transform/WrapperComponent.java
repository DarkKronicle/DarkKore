package io.github.darkkronicle.darkkore.gui.components.transform;

import io.github.darkkronicle.darkkore.gui.components.BasicComponent;
import io.github.darkkronicle.darkkore.gui.components.Component;
import io.github.darkkronicle.darkkore.util.PositionedRectangle;
import io.github.darkkronicle.darkkore.util.Rectangle;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;

/**
 * A {@link Component} meant to contain one {@link Component} and change its location. All methods here
 * simply forward the calls into the component.
 *
 * <p>For instance this is used for {@link ScrollComponent} and {@link PositionedComponent}
 */
public abstract class WrapperComponent extends BasicComponent {

    /** The width the component should be */
    @Setter
    protected int width;
    /** The height the component should be */
    @Setter
    protected int height;

    /** The component that is being handled */
    @Getter protected Component component;

    /** If the component's width should be used instead of {@link #width} */
    @Setter @Getter protected boolean useComponentWidth;

    /** If the component's height should be used instead of {@link #height} */
    @Setter @Getter protected boolean useComponentHeight;

    /**
     * Creates a {@link WrapperComponent} with specified width height. If width/height are less than 0,
     * then {@link #useComponentWidth}/{@link #useComponentHeight} are set respectively.
     * @param parent
     * @param component The {@link Component} to wrap
     * @param width Width to use. If less than 0 the width of the {@link #component} is used
     * @param height Height to use. If less than 0 the height of the {@link #component} is used
     */
    public WrapperComponent(Screen parent, Component component, int width, int height) {
        super(parent);
        this.width = width;
        this.height = height;
        this.useComponentWidth = width < 0;
        this.useComponentHeight = height < 0;
        this.component = component;
        this.selectable = true;
    }

    /** {@inheritDoc} */
    @Override
    public boolean mouseClickedImpl(int x, int y, int mouseX, int mouseY, int button) {
        if (isHovered()) {
            return component.mouseClicked(x, y, mouseX, mouseY, button);
        }
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public void mouseClickedOutsideImpl(int x, int y, int mouseX, int mouseY, int button) {
        component.mouseClickedOutside(x, y, mouseX, mouseY, button);
    }

    /** {@inheritDoc} */
    @Override
    public boolean mouseScrolled(int x, int y, int mouseX, int mouseY, double amount) {
        if (isHovered()) {
            return component.mouseScrolled(x, y, mouseX, mouseY, amount);
        }
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public Rectangle getBoundingBox() {
        Rectangle bound = component.getBoundingBox();
        return new Rectangle(useComponentWidth ? bound.width() : width, useComponentHeight ? bound.height() : height);
    }
    /** {@inheritDoc} */
    @Override
    public void renderComponent(MatrixStack matrices, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY) {
        component.render(matrices, renderBounds, x, y, mouseX, mouseY);
    }

    /** {@inheritDoc} */
    @Override
    public void postRender(MatrixStack matrices, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY) {
        if (component.shouldPostRender()) {
            component.postRender(matrices, renderBounds, x, y, mouseX, mouseY);
        }
    }

    /** {@inheritDoc} */
    @Override
    public boolean shouldPostRender() {
        // We then check to see if the component should post render, so just always call this
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public boolean charTyped(char key, int modifiers) {
        if (component.isSelected()) {
            return component.charTyped(key, modifiers);
        }
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (component.isSelected()) {
            return component.keyPressed(keyCode, scanCode, modifiers);
        }
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public void onDestroy() {
        component.onDestroy();
    }

}
