package io.github.darkkronicle.darkkore.gui.components.transform;

import io.github.darkkronicle.darkkore.gui.components.Component;
import io.github.darkkronicle.darkkore.util.PositionedRectangle;
import io.github.darkkronicle.darkkore.util.Rectangle;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.util.math.MatrixStack;

/**
 * A {@link MultiComponent} that automatically lists {@link Component}s with specified spacing and can handle
 * adding and removing really well.
 *
 * <p>Perfect for any list sort of interface. Options, buttons...
 */
public class ListComponent extends MultiComponent {

    /** Should this list be forced up and down, or should components be added left/right until it breaks into a new line */
    private final boolean vertical;

    /** The padding from the left where elements shouldn't go. Essentially a left margin. */
    @Getter @Setter protected int leftPad = 2;
    /** The padding from the right where elements shouldn't go. Essentially a right margin. */
    @Getter @Setter protected int rightPad = 2;
    /** The padding from the top where elements shouldn't go. Essentially a top margin. */
    @Getter @Setter protected int topPad = 2;
    /** The padding from the bottom where elements shouldn't go. Essentially a bottom margin. */
    @Getter @Setter protected int bottomPad = 2;

    /** Padding between components left/right */
    @Getter @Setter protected int componentXPad = 2;
    /** Padding between components up/down */
    @Getter @Setter protected int componentYPad = 2;

    /** If this component should render post. If this is set to false then children won't be rendered post. */
    @Getter @Setter private boolean renderPost = true;

    /**
     * Creates a new {@link ListComponent}
     *
     * <p> If width is less than 0 it automatically updates width. If height is less than 0 it automatically updates height.
     *
     * <p>Vertical is the order in how components should render. If it's set to true it always will
     * render each component on a new line, if it is false it will render left to right until it has to break
     * into a new one.
     *
     * <p> If vertical is set to false and width is auto update, no new lines will be added. If vertical is true and height is not auto update,
     * only the first few elements will render. It is recommended to have height be auto update.
     * @param width Width
     * @param height Height
     * @param vertical Vertical
     */
    public ListComponent(int width, int height, boolean vertical) {
        super(width, height);
        this.vertical = vertical;
    }

    /** {@inheritDoc} */
    @Override
    public void updateWidth() {
        if (vertical) {
            // If vertical it should just be the widest one
            int maxWidth = 0;
            for (Component component : components) {
                maxWidth = Math.max(maxWidth, component.getBoundingBox().width());
            }
            width = maxWidth + leftPad + rightPad;
            return;
        }
        // If not vertical just add all of them together (including padding)
        int totalWidth = 0;
        for (Component component : components) {
            totalWidth += component.getBoundingBox().width() + componentXPad;
        }
        width = totalWidth + leftPad + rightPad;
    }

    /** {@inheritDoc} */
    public void updateHeight() {
        if (vertical) {
            // If vertical it should be the combination of everything.
            int totalHeight = 0;
            for (Component component : components) {
                totalHeight += component.getBoundingBox().height() + componentYPad;
            }
            height = totalHeight + topPad + bottomPad;
            return;
        }
        // If not vertical should go through each line as in render
        int offsetRenderX = leftPad;
        int renderY = 0;
        int maxHeight = 0;
        for (Component component : components) {
            Rectangle bounds = component.getBoundingBox();
            if (bounds.width() + offsetRenderX + rightPad > width || vertical) {
                // New line
                offsetRenderX = leftPad;
                renderY += maxHeight + componentYPad;
                maxHeight = 0;
            }
            offsetRenderX += bounds.width() + componentXPad;
            maxHeight = Math.max(maxHeight, bounds.height());
        }
        height = renderY + maxHeight + topPad + bottomPad;
    }


    /** {@inheritDoc} */
    @Override
    public void renderComponent(MatrixStack matrices, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY) {
        // Setup left margin
        int offsetRenderX = leftPad;
        // Setup top margin
        int renderY = y + topPad;
        // This is set so that we can properly create a new line and have no overlap
        int maxHeight = 0;
        // Remember what component is hoverered so we can pass events
        hoveredComponent = null;
        for (Component component : components) {
            Rectangle bounds = component.getBoundingBox();
            if (vertical || bounds.width() + offsetRenderX + rightPad > width) {
                // Create new line
                offsetRenderX = leftPad;
                renderY += maxHeight + componentYPad;
                maxHeight = 0;
            }
            if (renderBounds == null || new PositionedRectangle(x + offsetRenderX, renderY, bounds.width(), bounds.height()).intersects(renderBounds)) {
                // Check if we are within the render bounds. Makes it so we don't render things off-screen.
                component.render(matrices, renderBounds, x + offsetRenderX, renderY, mouseX, mouseY);
            }
            // Move to the right
            offsetRenderX += bounds.width() + componentXPad;
            maxHeight = Math.max(maxHeight, bounds.height());
            if (component.isHovered()) {
                hoveredComponent = component;
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public boolean shouldPostRender() {
        return renderPost;
    }

    /** {@inheritDoc} */
    @Override
    public void postRender(MatrixStack matrices, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY) {
        // We do the same thing as normal render again
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
            if (component.shouldPostRender() && (renderBounds == null || new PositionedRectangle(x + offsetRenderX, renderY, bounds.width(), bounds.height()).intersects(renderBounds))) {
                component.postRender(matrices, renderBounds, x + offsetRenderX, renderY, mouseX, mouseY);
            }
            offsetRenderX += bounds.width() + componentXPad;
            maxHeight = Math.max(maxHeight, bounds.height());
            if (component.isHovered()) {
                hoveredComponent = component;
            }
        }
    }
}
