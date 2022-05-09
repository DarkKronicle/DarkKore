package io.github.darkkronicle.darkkore.gui.components;

import io.github.darkkronicle.darkkore.util.PositionedRectangle;
import io.github.darkkronicle.darkkore.util.Rectangle;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.util.math.MatrixStack;
import org.w3c.dom.css.Rect;

public class PositionedComponent extends BasicComponent {

    private int x;
    private int y;
    private int width;
    private int height;

    @Getter
    private Component component;

    @Setter @Getter
    private boolean useComponentWidth;
    @Setter @Getter
    private boolean useComponentHeight;

    public PositionedComponent(Component component, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.useComponentWidth = width < 0;
        this.useComponentHeight = height < 0;
        this.component = component;
    }

    @Override
    public boolean mouseClicked(int x, int y, int mouseX, int mouseY) {
        if (isHovered()) {
            return component.mouseClicked(x + this.x, y + this.y, mouseX, mouseY);
        }
        return false;
    }

    @Override
    public boolean mouseScrolled(int x, int y, int mouseX, int mouseY, double amount) {
        if (isHovered()) {
            return component.mouseScrolled(x + this.x, y + this.y, mouseX, mouseY, amount);
        }
        return false;
    }


    @Override
    public Rectangle getBoundingBox() {
        Rectangle bound = component.getBoundingBox();
        return new Rectangle(useComponentWidth ? bound.width() : width, useComponentHeight ? bound.height() : height);
    }

    @Override
    public void render(MatrixStack matrices, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY) {
        Rectangle bound = getBoundingBox();
        super.render(matrices, new PositionedRectangle(x + this.x, y + this.y, bound.width(), bound.height()), x + this.x, y + this.y, mouseX, mouseY);
    }

    @Override
    public void renderComponent(MatrixStack matrices, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY) {
        component.render(matrices, renderBounds, x, y, mouseX, mouseY);
    }

}
