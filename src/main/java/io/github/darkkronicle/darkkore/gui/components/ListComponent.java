package io.github.darkkronicle.darkkore.gui.components;

import io.github.darkkronicle.darkkore.util.PositionedRectangle;
import io.github.darkkronicle.darkkore.util.Rectangle;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.util.math.MatrixStack;

public class ListComponent extends MultiComponent {

    private boolean vertical;

    @Getter @Setter private int leftPad = 2;
    @Getter @Setter private int rightPad = 2;
    @Getter @Setter private int topPad = 2;
    @Getter @Setter private int bottomPad = 2;

    @Getter @Setter private int componentXPad = 2;
    @Getter @Setter private int componentYPad = 2;


    public ListComponent(int width, int height, boolean vertical) {
        super(width, height);
        this.vertical = vertical;
    }

    @Override
    public void updateWidth() {
        if (vertical) {
            int maxWidth = 0;
            for (Component component : components) {
                maxWidth = Math.max(maxWidth, component.getBoundingBox().width());
            }
            width = maxWidth + leftPad + rightPad;
            return;
        }
        int totalWidth = 0;
        for (Component component : components) {
            totalWidth += component.getBoundingBox().width() + componentXPad;
        }
        width = totalWidth + leftPad + rightPad;
    }

    public void updateHeight() {
        if (vertical) {
            int totalHeight = 0;
            for (Component component : components) {
                totalHeight += component.getBoundingBox().height() + componentYPad;
            }
            height = totalHeight + topPad + bottomPad;
            return;
        }
        int offsetRenderX = leftPad;
        int renderY = 0;
        int maxHeight = 0;
        for (Component component : components) {
            Rectangle bounds = component.getBoundingBox();
            if (bounds.width() + offsetRenderX + rightPad > width || vertical) {
                offsetRenderX = leftPad;
                renderY += maxHeight + componentYPad;
                maxHeight = 0;
            }
            offsetRenderX += bounds.width() + componentXPad;
            maxHeight = Math.max(maxHeight, bounds.height());
        }
        height = renderY + maxHeight + topPad + bottomPad;
    }


    @Override
    public void renderComponent(MatrixStack matrices, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY) {
        int offsetRenderX = leftPad;
        int renderY = y + topPad;
        int maxHeight = 0;
        hoveredComponent = null;
        for (Component component : components) {
            Rectangle bounds = component.getBoundingBox();
            if (bounds.width() + offsetRenderX + rightPad > width || vertical) {
                offsetRenderX = leftPad;
                renderY += maxHeight + componentYPad;
                maxHeight = 0;
            }
            if (renderBounds == null || new PositionedRectangle(x + offsetRenderX, renderY, bounds.width(), bounds.height()).intersects(renderBounds)) {
                component.render(matrices, renderBounds, x + offsetRenderX, renderY, mouseX, mouseY);
            }
            offsetRenderX += bounds.width() + componentXPad;
            maxHeight = Math.max(maxHeight, bounds.height());
            if (component.isHovered()) {
                hoveredComponent = component;
            }
        }
    }

}
