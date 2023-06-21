package io.github.darkkronicle.darkkore.gui.components;

import io.github.darkkronicle.darkkore.util.PositionedRectangle;
import io.github.darkkronicle.darkkore.util.Rectangle;
import net.minecraft.client.gui.DrawContext;

/**
 * An interface for handling GUI components within DarkKore.
 *
 * <p>{@link BasicComponent} has many of these implemented and different methods for easier creation
 * of new components. Each component should fit well with each other in most situations.
 */
public interface Component {

    /**
     * Renders the component. Components should not have a position assigned to them. They should take in whatever
     * x/y value is assigned and then render based off that position.
     * If position is needed use {@link io.github.darkkronicle.darkkore.gui.components.transform.PositionedComponent}
     * @param context {@link DrawContext} to invoke other render methods
     * @param renderBounds {@link PositionedRectangle} an object that contains the boundaries of the object being rendered
     * @param x The x value where the component should render
     * @param y The y value where the component should render
     * @param mouseX The x value of the mouse
     * @param mouseY The y value of the mouse
     */
    void render(DrawContext context, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY);

    /**
     * The bounding box (width height) of the component. This does not contain x/y data.
     * @return {@link Rectangle} with specified width and height
     */
    Rectangle getBoundingBox();

    /**
     * Renders the component after everything has already been rendered. This should be used if overlapping is needed.
     * This is used for hover descriptions.
     * @param context {@link DrawContext} to invoke other render methods
     * @param renderBounds {@link PositionedRectangle} an object that contains the boundaries of the object being rendered
     * @param x The x value where the component should render
     * @param y The y value where the component should render
     * @param mouseX The x value of the mouse
     * @param mouseY The y value of the mouse
     */
    default void postRender(DrawContext context, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY) {}

    /**
     * Whether this component should render post. If false {@link #postRender(DrawContext, PositionedRectangle, int, int, int, int)}
     * won't be called
     * @return True if should post render
     */
    default boolean shouldPostRender() {
        return false;
    }

    /**
     * The method for when a mouse is clicked on this component. Components typically check if the mouse
     * is currently on this component before calling this, so you shouldn't need to add a check.
     * @param x The x value where the component should render
     * @param y The y value where the component should render
     * @param mouseX The x value of the mouse
     * @param mouseY The y value of the mouse
     * @param button Mouse button. 0 is left click, 1 is right click, 2 is middle
     * @return True if this should stop other components from responding. (Basically if it successfully was processed
     * and was a valid action)
     */
    default boolean mouseClicked(int x, int y, int mouseX, int mouseY, int button) {
        return false;
    }

    /**
     * The method for when a mouse is clicked outside this component. This could be useful if a menu or selection
     * needs to be canceled if clicked away.
     * @param x The x value where the component should render
     * @param y The y value where the component should render
     * @param mouseX The x value of the mouse
     * @param mouseY The y value of the mouse
     * @param button Mouse button. 0 is left click, 1 is right click, 2 is middle
     */
    default void mouseClickedOutside(int x, int y, int mouseX, int mouseY, int button) {}

    /**
     * The method for when the mouse scrolls inside this component. Components typically check if the mouse
     * is currently on this component before calling this, so you shouldn't need to add a check.
     * @param x The x value where the component should render
     * @param y The y value where the component should render
     * @param mouseX The x value of the mouse
     * @param mouseY The y value of the mouse
     * @param amount The amount of scroll
     * @return True if this should stop other components from responding. (Basically if it successfully was processed
     * and was a valid action)
     */
    default boolean mouseScrolled(int x, int y, int mouseX, int mouseY, double amount) {
        return false;
    }

    /**
     * The method for when a character is typed and <b>this component {@link #isSelected()}</b>
     * @param key The character that was typed
     * @param modifiers Modifiers held down with the character
     * @return True if this should stop other components from responding. (Basically if it successfully was processed
     * and was a valid action)
     */
    default boolean charTyped(char key, int modifiers) {
        return false;
    }

    /**
     * The method for when a key is pressed and <b>this component {@link #isSelected()}</b>
     * @param keyCode The key that was pressed
     * @param scanCode The scan code of the key
     * @param modifiers Modifiers held down with the character
     * @return True if this should stop other components from responding. (Basically if it successfully was processed
     * and was a valid action)
     */
    default boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    /**
     * The method for when the component's hovered status is changed
     * @param x The x value where the component should render
     * @param y The y value where the component should render
     * @param mouseX The x value of the mouse
     * @param mouseY The y value of the mouse
     * @param hovered If the component is now currently hovered or not
     */
    default void onHovered(int x, int y, int mouseX, int mouseY, boolean hovered) {}

    /**
     * Whether this component is hovered currently
     * @return If hovered
     */
    default boolean isHovered() {
        return false;
    }

    /**
     * Whether this component is selected currently. Typically is if the mouse clicks on this object
     * @return If Selected
     */
    default boolean isSelected() {
        return false;
    }

    /**
     * Called when the screen closes/the element is destroyed
     */
    default void onDestroy() {}

}
