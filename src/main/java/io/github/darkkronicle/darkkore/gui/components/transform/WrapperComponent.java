package io.github.darkkronicle.darkkore.gui.components.transform;

import io.github.darkkronicle.darkkore.gui.components.BasicComponent;
import io.github.darkkronicle.darkkore.gui.components.Component;
import io.github.darkkronicle.darkkore.util.PositionedRectangle;
import io.github.darkkronicle.darkkore.util.Rectangle;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.util.math.MatrixStack;

public abstract class WrapperComponent extends BasicComponent {

    protected int width;
    protected int height;

    @Getter
    protected Component component;

    @Setter
    @Getter
    protected boolean useComponentWidth;
    @Setter
    @Getter
    protected boolean useComponentHeight;

    public WrapperComponent(Component component, int width, int height) {
        this.width = width;
        this.height = height;
        this.useComponentWidth = width < 0;
        this.useComponentHeight = height < 0;
        this.component = component;
        this.selectable = true;
    }

    @Override
    public boolean mouseClicked(int x, int y, int mouseX, int mouseY, int button) {
        super.mouseClicked(x, y, mouseX, mouseY, button);
        if (isHovered()) {
            return component.mouseClicked(x, y, mouseX, mouseY, button);
        }
        return false;
    }

    @Override
    public void mouseClickedOutside(int x, int y, int mouseX, int mouseY) {
        super.mouseClickedOutside(x, y, mouseX, mouseY);
        component.mouseClickedOutside(x, y, mouseX, mouseY);
    }

    @Override
    public boolean mouseScrolled(int x, int y, int mouseX, int mouseY, double amount) {
        if (isHovered()) {
            return component.mouseScrolled(x, y, mouseX, mouseY, amount);
        }
        return false;
    }

    @Override
    public Rectangle getBoundingBox() {
        Rectangle bound = component.getBoundingBox();
        return new Rectangle(useComponentWidth ? bound.width() : width, useComponentHeight ? bound.height() : height);
    }

    @Override
    public void renderComponent(MatrixStack matrices, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY) {
        component.render(matrices, renderBounds, x, y, mouseX, mouseY);
    }

    @Override
    public void postRender(MatrixStack matrices, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY) {
        if (component.shouldPostRender()) {
            component.postRender(matrices, renderBounds, x, y, mouseX, mouseY);
        }
    }

    @Override
    public boolean shouldPostRender() {
        return true;
    }

    @Override
    public boolean charTyped(char key, int modifiers) {
        if (component.isSelected()) {
            return component.charTyped(key, modifiers);
        }
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (component.isSelected()) {
            return component.keyPressed(keyCode, scanCode, modifiers);
        }
        return false;
    }

}
