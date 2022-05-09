package io.github.darkkronicle.darkkore.gui.components;

import io.github.darkkronicle.darkkore.util.Color;
import io.github.darkkronicle.darkkore.util.PositionedRectangle;
import io.github.darkkronicle.darkkore.util.Rectangle;
import io.github.darkkronicle.darkkore.util.RenderUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.minecraft.client.util.math.MatrixStack;

import java.util.function.Consumer;

@Accessors(chain = true)
public abstract class BasicComponent implements Component {

    @Getter @Setter private Color backgroundColor;

    @Getter @Setter private Color outlineColor;

    @Getter @Setter private Consumer<BasicComponent> onHoveredConsumer = null;
    @Getter @Setter private Consumer<BasicComponent> onHoveredStoppedConsumer = null;

    @Getter private boolean hovered = false;
    private boolean previouslyHovered = false;

    public BasicComponent() {
        this(null, null);
    }

    public BasicComponent(Color backgroundColor, Color outlineColor) {
        this.backgroundColor = backgroundColor;
        this.outlineColor = outlineColor;
    }

    @Override
    public void render(MatrixStack matrices, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY) {
        if (backgroundColor != null) {
            Rectangle bounds = getBoundingBox();
            RenderUtil.drawRectangle(matrices, x, y, bounds.width(), bounds.height(), backgroundColor.color());
        }
        if (outlineColor != null) {
            Rectangle bounds = getBoundingBox();
            RenderUtil.drawOutline(matrices, x, y, bounds.width(), bounds.height(), outlineColor.color());
        }
        Rectangle bounds = getBoundingBox();
        if (mouseX >= x && mouseX <= x + bounds.width() && mouseY >= y && mouseY <= y + bounds.height()) {
            hovered = true;
            if (!previouslyHovered) {
                onHovered(x, y, mouseX, mouseY);
            }
            previouslyHovered = true;
        } else {
            hovered = false;
            if (previouslyHovered) {
                onHoveredStopped(x, y, mouseX, mouseY);
            }
            previouslyHovered = false;
        }
        renderComponent(matrices, renderBounds, x, y, mouseX, mouseY);
    }

    public abstract void renderComponent(MatrixStack matrices, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY);

    @Override
    public void onHovered(int x, int y, int mouseX, int mouseY) {
        if (onHoveredConsumer != null) {
            onHoveredConsumer.accept(this);
        }
        this.onHoveredImpl(x, y, mouseX, mouseY);
    }

    public void onHoveredImpl(int x, int y, int mouseX, int mouseY) {

    }

    @Override
    public void onHoveredStopped(int x, int y, int mouseX, int mouseY) {
        if (onHoveredStoppedConsumer != null) {
            onHoveredStoppedConsumer.accept(this);
        }
        this.onHoveredStoppedImpl(x, y, mouseX, mouseY);
    }

    public void onHoveredStoppedImpl(int x, int y, int mouseX, int mouseY) {

    }
}