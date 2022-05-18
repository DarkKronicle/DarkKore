package io.github.darkkronicle.darkkore.gui.components.transform;

import io.github.darkkronicle.darkkore.gui.components.Component;
import io.github.darkkronicle.darkkore.util.PositionedRectangle;
import io.github.darkkronicle.darkkore.util.Rectangle;
import net.minecraft.client.util.math.MatrixStack;

public abstract class OffsetComponent extends WrapperComponent {

    public OffsetComponent(Component component, int width, int height) {
        super(component, width, height);
    }

    public abstract int getXOffset();

    public abstract int getYOffset();

    @Override
    public void render(MatrixStack matrices, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY) {
        Rectangle bound = getBoundingBox();
        int offX = this.getXOffset();
        int offY = this.getYOffset();
        super.render(matrices, new PositionedRectangle(x + offX, y + offY, bound.width(), bound.height()), x + offX, y + offY, mouseX, mouseY);
    }

    @Override
    public void postRender(MatrixStack matrices, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY) {
        if (component.shouldPostRender()) {
            int offX = this.getXOffset();
            int offY = this.getYOffset();
            component.postRender(matrices, renderBounds, x + offX, y + offY, mouseX, mouseY);
        }
    }

    @Override
    public Rectangle getBoundingBox() {
        Rectangle bound = component.getBoundingBox();
        return new Rectangle(useComponentWidth ? bound.width() : width, useComponentHeight ? bound.height() : height);
    }

    @Override
    public boolean mouseScrolled(int x, int y, int mouseX, int mouseY, double amount) {
        if (isHovered()) {
            int offX = this.getXOffset();
            int offY = this.getYOffset();
            return component.mouseScrolled(x + offX, y + offY, mouseX, mouseY, amount);
        }
        return false;
    }

    @Override
    public void mouseClickedOutside(int x, int y, int mouseX, int mouseY) {
        super.mouseClickedOutside(x, y, mouseX, mouseY);
        int offX = this.getXOffset();
        int offY = this.getYOffset();
        component.mouseClickedOutside(x + offX, y + offY, mouseX, mouseY);
    }

}
