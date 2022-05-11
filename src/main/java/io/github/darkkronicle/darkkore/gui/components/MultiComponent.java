package io.github.darkkronicle.darkkore.gui.components;

import io.github.darkkronicle.darkkore.util.PositionedRectangle;
import io.github.darkkronicle.darkkore.util.Rectangle;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MultiComponent extends BasicComponent {

    private boolean autoUpdateWidth;
    private boolean autoUpdateHeight;
    @Setter protected int width;
    @Setter protected int height;

    @Getter
    protected List<Component> components = new ArrayList<>();

    public MultiComponent(int width, int height) {
        this.width = width;
        this.height = height;
        if (width < 0) {
            autoUpdateWidth = true;
        }
        if (height < 0) {
            autoUpdateHeight = true;
        }
    }

    @Override
    public void renderComponent(MatrixStack matrices, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY) {
        for (Component component : components) {
            component.render(matrices, renderBounds, x, y, mouseX, mouseY);
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
}
