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


    @Getter @Setter protected int zOffset = 0;

    @Getter private boolean hovered = false;
    private boolean previouslyHovered = false;

    protected boolean selectable = false;
    private boolean previouslySelected = false;
    private boolean selected = false;

    public BasicComponent() {
        this(null, null);
    }

    public BasicComponent(Color backgroundColor, Color outlineColor) {
        this.backgroundColor = backgroundColor;
        this.outlineColor = outlineColor;
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void mouseClickedOutside(int x, int y, int mouseX, int mouseY) {
        selected = false;
        if (previouslySelected) {
            previouslySelected = false;
            onSelectedImpl(false);
        }
    }

    @Override
    public boolean mouseClicked(int x, int y, int mouseX, int mouseY, int button) {
        if (selectable) {
            selected = true;
            if (!previouslySelected) {
                previouslySelected = true;
                onSelectedImpl(true);
            }
        }
        return false;
    }

    @Override
    public void render(MatrixStack matrices, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY) {
        matrices.push();
        if (zOffset != 0) {
            matrices.translate(0, 0, zOffset);
        }
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
        matrices.pop();
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

    public void onSelectedImpl(boolean selected) {

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