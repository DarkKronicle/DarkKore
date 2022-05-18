package io.github.darkkronicle.darkkore.gui.components;

import io.github.darkkronicle.darkkore.util.PositionedRectangle;
import io.github.darkkronicle.darkkore.util.Rectangle;
import net.minecraft.client.util.math.MatrixStack;

public interface Component {

    void render(MatrixStack matrices, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY);

    default void postRender(MatrixStack matrices, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY) {}

    default boolean shouldPostRender() {
        return false;
    }

    Rectangle getBoundingBox();

    default boolean mouseClicked(int x, int y, int mouseX, int mouseY, int button) {
        return false;
    }

    default void mouseClickedOutside(int x, int y, int mouseX, int mouseY) {

    }

    default boolean mouseScrolled(int x, int y, int mouseX, int mouseY, double amount) {
        return false;
    }

    default boolean charTyped(char key, int modifiers) {
        return false;
    }

    default boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    default void onHovered(int x, int y, int mouseX, int mouseY) {}

    default boolean isHovered() {
        return false;
    }

    default boolean isSelected() {
        return false;
    }

    default void onHoveredStopped(int x, int y, int mouseX, int mouseY) {}

}
