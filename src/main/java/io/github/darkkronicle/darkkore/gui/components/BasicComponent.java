package io.github.darkkronicle.darkkore.gui.components;

import io.github.darkkronicle.darkkore.util.Color;
import io.github.darkkronicle.darkkore.util.PositionedRectangle;
import io.github.darkkronicle.darkkore.util.Rectangle;
import io.github.darkkronicle.darkkore.util.render.RenderUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * The main implementation of {@link Component} to make handling rendering, selection, and other events
 * easier to manage and deal with.
 */
@Accessors(chain = true)
public abstract class BasicComponent implements Component {

    /** The color that the background should be rendered. If null it doesn't render. */
    @Nullable
    @Getter @Setter private Color backgroundColor;

    /** The color that the outline should be rendered. If null it doesn't render. */
    @Nullable
    @Getter @Setter private Color outlineColor;

    /** A {@link Consumer} of this when hovered */
    @Getter @Setter private Consumer<BasicComponent> onHoveredConsumer = null;

    /** A {@link Consumer} of this when stopped hovered */
    @Getter @Setter private Consumer<BasicComponent> onHoveredStoppedConsumer = null;

    @Getter @Setter private Consumer<BasicComponent> onClickedConsumer = null;

    /** A z offset to apply right before rendering */
    @Getter @Setter protected int zOffset = 0;

    /** {@inheritDoc} */
    @Getter private boolean hovered = false;

    /** If in the last render cycle this component was hovered */
    private boolean previouslyHovered = false;

    /**
     * If this component should be selectable. If this is false {@link #charTyped(char, int)} and other similar
     * ones may not end up being called.
     * */
    protected boolean selectable = false;

    /** If this component was selected last mouse click */
    private boolean previouslySelected = false;

    /**
     * If currently selected.
     * <p>Setting this manually could cause some problems if other components aren't deselected first.
     */
    protected boolean selected = false;

    /**
     * Constructs a {@link BasicComponent} with no background or outline color
     */
    public BasicComponent() {
        this(null, null);
    }

    /**
     * Constructs a {@link BasicComponent} with a background and outline {@link Color}
     * @param backgroundColor Color to render the background. If null no background.
     * @param outlineColor Color to render the outline. If null no outline.
     */
    public BasicComponent(@Nullable Color backgroundColor, @Nullable  Color outlineColor) {
        this.backgroundColor = backgroundColor;
        this.outlineColor = outlineColor;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isSelected() {
        return selected;
    }

    /**
     * An implementation of {@link Component#mouseClickedOutside(int, int, int, int, int)} but this handles selection.
     * If this event is needed override {@link #mouseClickedOutsideImpl(int, int, int, int, int)}
     * @param x The x value where the component should render
     * @param y The y value where the component should render
     * @param mouseX The x value of the mouse
     * @param mouseY The y value of the mouse
     * @param button Mouse button. 0 is left click, 1 is right click, 2 is middle
     */
    @Override
    public void mouseClickedOutside(int x, int y, int mouseX, int mouseY, int button) {
        selected = false;
        setSelected(selected);
        mouseClickedOutsideImpl(x, y, mouseX, mouseY, button);
    }

    /**
     * A method called after {@link #mouseClickedOutside(int, int, int, int, int)} that can be overridden
     * and still retain selection. No necessary call to super.
     * @param x The x value where the component should render
     * @param y The y value where the component should render
     * @param mouseX The x value of the mouse
     * @param mouseY The y value of the mouse
     * @param button Mouse button. 0 is left click, 1 is right click, 2 is middle
     */
    public void mouseClickedOutsideImpl(int x, int y, int mouseX, int mouseY, int button) {}

    /**
     * An implementation of {@link Component#mouseClicked(int, int, int, int, int)} but this handles selection.
     * If this event is needed override {@link #mouseClickedImpl(int, int, int, int, int)}
     * @param x The x value where the component should render
     * @param y The y value where the component should render
     * @param mouseX The x value of the mouse
     * @param mouseY The y value of the mouse
     * @param button Mouse button. 0 is left click, 1 is right click, 2 is middle
     */
    @Override
    public boolean mouseClicked(int x, int y, int mouseX, int mouseY, int button) {
        if (selectable) {
            setSelected(true);
        }
        if (onClickedConsumer != null) {
            onClickedConsumer.accept(this);
        }
        return mouseClickedImpl(x, y, mouseX, mouseY, button);
    }

    /**
     * Sets if this component is selected
     * <p>This can break stuff if other stuff isn't unselected first, so be careful
     * @param value Selected
     */
    public void setSelected(boolean value) {
        selected = value;
        if (previouslySelected != value) {
            previouslySelected = value;
            onSelectedImpl(value);
        }
    }

    /**
     * A method called when this component is selected
     * @param selected If selected or not
     */
    public void onSelectedImpl(boolean selected) {}

    /**
     * A method called after {@link #mouseClicked(int, int, int, int, int)} that can be overridden
     * and still retain selection. No necessary call to super.
     * @param x The x value where the component should render
     * @param y The y value where the component should render
     * @param mouseX The x value of the mouse
     * @param mouseY The y value of the mouse
     * @param button Mouse button. 0 is left click, 1 is right click, 2 is middle
     */
    public boolean mouseClickedImpl(int x, int y, int mouseX, int mouseY, int button) {
        return false;
    }

    /**
     * An implementation of {@link Component#render(MatrixStack, PositionedRectangle, int, int, int, int)}. 
     * This render method automatically handles {@link #backgroundColor}, {@link #outlineColor}, {@link #selected},
     * {@link #hovered}, and other various utilities. 
     * 
     * <p>You shouldn't override this. Instead override {@link #renderComponent(MatrixStack, PositionedRectangle, int, int, int, int)}
     * @param matrices {@link MatrixStack} to invoke other render methods
     * @param renderBounds {@link PositionedRectangle} an object that contains the boundaries of the object being rendered
     * @param x The x value where the component should render
     * @param y The y value where the component should render
     * @param mouseX The x value of the mouse
     * @param mouseY The y value of the mouse
     */
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
        if (checkIfHovered(renderBounds, x, y, mouseX, mouseY)) {
            hovered = true;
            if (!previouslyHovered) {
                onHovered(x, y, mouseX, mouseY, true);
            }
            previouslyHovered = true;
        } else {
            hovered = false;
            if (previouslyHovered) {
                onHovered(x, y, mouseX, mouseY, false);
            }
            previouslyHovered = false;
        }
        renderComponent(matrices, renderBounds, x, y, mouseX, mouseY);
        matrices.pop();
    }

    /**
     * A method to render the component. This should be used in almost all cases.
     * @param matrices {@link MatrixStack} to invoke other render methods
     * @param renderBounds {@link PositionedRectangle} an object that contains the boundaries of the object being rendered
     * @param x The x value where the component should render
     * @param y The y value where the component should render
     * @param mouseX The x value of the mouse
     * @param mouseY The y value of the mouse
     */
    public abstract void renderComponent(MatrixStack matrices, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY);

    /**
     * Checks to see if the current component is hovered by the mouse given values
     * @param x X to render
     * @param y Y to render
     * @param mouseX Mouse position
     * @param mouseY Mouse position
     * @return True if it is hovered
     */
    public boolean checkIfHovered(PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY) {
        Rectangle bounds = getBoundingBox();
        return new PositionedRectangle(x, y, bounds.width(), bounds.height()).isPointIn(mouseX, mouseY);
    }

    /**
     * An implementation of {@link Component#onHovered(int, int, int, int, boolean)}. This one handles {@link #onHoveredConsumer} and then
     * delegates to {@link #onHoveredImpl(int, int, int, int, boolean)}, which you should override if that is wanted.
     * @param x The x value where the component should render
     * @param y The y value where the component should render
     * @param mouseX The x value of the mouse
     * @param mouseY The y value of the mouse
     * @param hovered If the component is now currently hovered or not
     */
    @Override
    public void onHovered(int x, int y, int mouseX, int mouseY, boolean hovered) {
        if (onHoveredConsumer != null && hovered) {
            onHoveredConsumer.accept(this);
        }
        if (onHoveredStoppedConsumer != null && !hovered) {
            onHoveredStoppedConsumer.accept(this);
        }
        this.onHoveredImpl(x, y, mouseX, mouseY, hovered);
    }

    /**
     * A method for when the component's hovered state changes.
     * This method is called after {@link #onHoveredConsumer}
     * @param x The x value where the component should render
     * @param y The y value where the component should render
     * @param mouseX The x value of the mouse
     * @param mouseY The y value of the mouse
      @param hovered If the component is now currently hovered or not
     */
    public void onHoveredImpl(int x, int y, int mouseX, int mouseY, boolean hovered) {}

}