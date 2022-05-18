package io.github.darkkronicle.darkkore.gui.components.transform;

import io.github.darkkronicle.darkkore.gui.components.BasicComponent;
import io.github.darkkronicle.darkkore.gui.components.Component;
import io.github.darkkronicle.darkkore.util.PositionedRectangle;
import io.github.darkkronicle.darkkore.util.Rectangle;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * A {@link Component} designed to handle multiple components at once and properly delegate everything towards
 * its children.
 */
public class MultiComponent extends BasicComponent {

    /** Whether the width should update with each new component added */
    private boolean autoUpdateWidth;

    /** Whether the height should update with each new component added */
    private boolean autoUpdateHeight;

    /** The width of the full component. This should encapsulate everything (that should be accessible) */
    @Getter @Setter protected int width;

    /** The height of the full component. This should encapsulate everything (that should be accessible) */
    @Getter @Setter protected int height;

    /** The current child component that is hovered */
    @Getter protected Component hoveredComponent = null;

    /** All the children this class holds */
    @Getter protected List<Component> components = new ArrayList<>();

    /**
     * Constructs a {@link MultiComponent} with specified width and height. If width/height are below 0
     * their respective {@link #autoUpdateWidth}/{@link #autoUpdateHeight} are set to true.
     * @param width Width (if below 0 width is auto updated)
     * @param height Height (if below 1 height is auto updated)
     */
    public MultiComponent(int width, int height) {
        this.width = width;
        this.height = height;
        this.selectable = true;
        if (width < 0) {
            autoUpdateWidth = true;
        }
        if (height < 0) {
            autoUpdateHeight = true;
        }
    }

    /** {@inheritDoc} */
    @Override
    public void renderComponent(MatrixStack matrices, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY) {
        hoveredComponent = null;
        for (Component component : components) {
            component.render(matrices, renderBounds, x, y, mouseX, mouseY);
            if (component.isHovered()) {
                hoveredComponent = component;
            }
        }
    }

    /**
     * Apply something to each child
     * @param componentConsumer {@link Consumer} to accept children
     */
    public void forEach(Consumer<Component> componentConsumer) {
        components.forEach(componentConsumer);
    }

    public void addComponent(Component component) {
        components.add(component);
        if (autoUpdateWidth) {
            updateWidth();
        }
        if (autoUpdateHeight) {
            updateHeight();
        }
    }

    /**
     * Add a {@link Component} at a specific index
     * @param index Index to add into (normal list rules)
     * @param component {@link Component} component to insert
     */
    public void addComponent(int index, Component component) {
        components.add(index, component);
        if (autoUpdateWidth) {
            updateWidth();
        }
        if (autoUpdateHeight) {
            updateHeight();
        }
    }

    /**
     * Sets a {@link Component} at a certain index
     * @param index Index to set into (normal list rules)
     * @param component {@link Component} to override
     */
    public void setComponent(int index, Component component) {
        components.set(index, component);
        if (autoUpdateWidth) {
            updateWidth();
        }
        if (autoUpdateHeight) {
            updateHeight();
        }
    }

    /**
     * Retrieves a {@link Component} at a specific index
     * @param index Index of the component
     * @return The component
     */
    public Component getComponent(int index) {
        return components.get(index);
    }

    /**
     * Removes a {@link Component} at a specific index
     * @param index Index to remove at
     */
    public void removeComponent(int index) {
        components.remove(index);
        if (autoUpdateWidth) {
            updateWidth();
        }
        if (autoUpdateHeight) {
            updateHeight();
        }
    }

    /**
     * Removes all {@link Component}s
     */
    public void clear() {
        components.clear();
        if (autoUpdateWidth) {
            updateWidth();
        }
        if (autoUpdateHeight) {
            updateHeight();
        }
    }

    /** {@inheritDoc} */
    @Override
    public boolean mouseClickedImpl(int x, int y, int mouseX, int mouseY, int button) {
        boolean success = false;
        for (Component component : components) {
            if (hoveredComponent != null && hoveredComponent.equals(component)) {
                success = hoveredComponent.mouseClicked(x, y, mouseX, mouseY, button) || success;
            } else {
                component.mouseClickedOutside(x, y, mouseX, mouseY, button);
            }
        }
        return success;
    }

    /** {@inheritDoc} */
    @Override
    public boolean mouseScrolled(int x, int y, int mouseX, int mouseY, double amount) {
        if (hoveredComponent != null) {
            return hoveredComponent.mouseScrolled(x, y, mouseX, mouseY, amount);
        }
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(width, height);
    }

    /**
     * Update the width of the component. This factors in child components.
     */
    public void updateWidth() {
        int maxX = 0;
        for (Component component : components) {
            maxX = Math.max(maxX, component.getBoundingBox().width());
        }
        width = maxX;
    }

    /**
     * Update the width of the component. This factors in child components.
     */
    public void updateHeight() {
        int maxY = 0;
        for (Component component : components) {
            maxY = Math.max(maxY, component.getBoundingBox().height());
        }
        height = maxY;
    }

    @Override
    public void postRender(MatrixStack matrices, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY) {
        for (Component component : components) {
            if (component.shouldPostRender()) {
                component.postRender(matrices, renderBounds, x, y, mouseX, mouseY);
            }
        }
    }

    @Override
    public boolean shouldPostRender() {
        return true;
    }

    @Override
    public void mouseClickedOutsideImpl(int x, int y, int mouseX, int mouseY, int button) {
        for (Component component : components) {
            component.mouseClickedOutside(x, y, mouseX, mouseY, button);
        }
    }

    @Override
    public boolean charTyped(char key, int modifiers) {
        for (Component component : components) {
            if (component.isSelected()) {
                return component.charTyped(key, modifiers);
            }
        }
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        for (Component component : components) {
            if (component.isSelected()) {
                return component.keyPressed(keyCode, scanCode, modifiers);
            }
        }
        return false;
    }

}
