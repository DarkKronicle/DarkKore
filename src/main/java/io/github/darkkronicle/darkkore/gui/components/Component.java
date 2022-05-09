package io.github.darkkronicle.darkkore.gui.components;

import io.github.darkkronicle.darkkore.util.PositionedRectangle;
import io.github.darkkronicle.darkkore.util.Rectangle;
import net.minecraft.client.util.math.MatrixStack;

public interface Component {

    void render(MatrixStack matrices, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY);

    Rectangle getBoundingBox();

    default boolean mouseClicked(int x, int y, int mouseX, int mouseY) {
        return false;
    }

    default boolean mouseScrolled(int x, int y, int mouseX, int mouseY, double amount) {
        return false;
    }

    default boolean keyTyped(int x, int y, char key) {
        return false;
    }

    default void onHovered(int x, int y, int mouseX, int mouseY) {}

    default boolean isHovered() {
        return false;
    }

    default void onHoveredStopped(int x, int y, int mouseX, int mouseY) {}

}
