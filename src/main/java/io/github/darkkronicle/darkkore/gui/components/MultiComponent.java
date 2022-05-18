package io.github.darkkronicle.darkkore.gui.components;

import io.github.darkkronicle.darkkore.util.PositionedRectangle;
import io.github.darkkronicle.darkkore.util.Rectangle;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class MultiComponent extends BasicComponent {

    private boolean autoUpdateWidth;
    private boolean autoUpdateHeight;
    @Setter protected int width;
    @Setter protected int height;

    @Getter protected Component hoveredComponent = null;

    @Getter
    protected List<Component> components = new ArrayList<>();

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

    public void addComponent(int index, Component component) {
        components.add(index, component);
        if (autoUpdateWidth) {
            updateWidth();
        }
        if (autoUpdateHeight) {
            updateHeight();
        }
    }

    public void setComponent(int index, Component component) {
        components.set(index, component);
        if (autoUpdateWidth) {
            updateWidth();
        }
        if (autoUpdateHeight) {
            updateHeight();
        }
    }

    public void removeComponent(int index) {
        components.remove(index);
        if (autoUpdateWidth) {
            updateWidth();
        }
        if (autoUpdateHeight) {
            updateHeight();
        }
    }

    public void clear() {
        components.clear();
        if (autoUpdateWidth) {
            updateWidth();
        }
        if (autoUpdateHeight) {
            updateHeight();
        }
    }

    @Override
    public boolean mouseClicked(int x, int y, int mouseX, int mouseY) {
        super.mouseClicked(x, y, mouseX, mouseY);
        boolean success = false;
        for (Component component : components) {
            if (hoveredComponent != null && hoveredComponent.equals(component)) {
                success = hoveredComponent.mouseClicked(x, y, mouseX, mouseY) || success;
            } else {
                component.mouseClickedOutside(x, y, mouseX, mouseY);
            }
        }
        return success;
    }

    @Override
    public boolean mouseScrolled(int x, int y, int mouseX, int mouseY, double amount) {
        if (hoveredComponent != null) {
            return hoveredComponent.mouseScrolled(x, y, mouseX, mouseY, amount);
        }
        return false;
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(width, height);
    }

    public void updateWidth() {
        int maxX = 0;
        for (Component component : components) {
            maxX = Math.max(maxX, component.getBoundingBox().width());
        }
        width = maxX;
    }

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
    public void mouseClickedOutside(int x, int y, int mouseX, int mouseY) {
        for (Component component : components) {
            component.mouseClickedOutside(x, y, mouseX, mouseY);
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
