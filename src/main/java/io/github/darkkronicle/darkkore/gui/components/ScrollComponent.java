package io.github.darkkronicle.darkkore.gui.components;

import io.github.darkkronicle.darkkore.util.*;
import lombok.Getter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Util;

public class ScrollComponent extends BasicComponent {

    @Getter
    private Component component;

    private int width;
    private int height;


    private double scrollStart = 0;
    private double scrollEnd = 0;
    private int scrollVal = 0;

    private long lastScroll = 0;
    private int scrollDuration = 200;

    private final boolean vertical;


    public ScrollComponent(Component component, int width, int height, boolean vertical) {
        this.width = width;
        this.height = height;
        this.component = component;
        this.vertical = vertical;
        this.selectable = true;
    }

    @Override
    public void renderComponent(MatrixStack matrices, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY) {
        updateScroll();
        renderBounds = new PositionedRectangle(x, y, width, height);
        ScissorsStack.getInstance().push(renderBounds);
        ScissorsStack.getInstance().applyStack();
        component.render(matrices, renderBounds, vertical ? x : x - scrollVal, vertical ? y - scrollVal : y, mouseX, mouseY);
        ScissorsStack.getInstance().pop();
        ScissorsStack.getInstance().applyStack();
    }

    @Override
    public boolean mouseClicked(int x, int y, int mouseX, int mouseY) {
        super.mouseClicked(x, y, mouseX, mouseY);
        return component.mouseClicked(vertical ? x : x - scrollVal, vertical ? y - scrollVal : y, mouseX, mouseY);
    }

    @Override
    public void mouseClickedOutside(int x, int y, int mouseX, int mouseY) {
        super.mouseClickedOutside(x, y, mouseX, mouseY);
        component.mouseClickedOutside(x, y, mouseX, mouseY);
    }

    @Override
    public boolean mouseScrolled(int x, int y, int mouseX, int mouseY, double amount) {
        if (component.mouseScrolled(vertical ? x : x - scrollVal, vertical ? y - scrollVal : y, mouseX, mouseY, amount)) {
            return true;
        }
        scroll(amount * 30);
        return true;
    }

    public void updateScroll() {
        long time = Util.getMeasuringTimeMs();
        scrollDuration = 300;
        scrollVal = (int) (scrollStart + (
                (scrollEnd - scrollStart) * (1 - (EasingMethod.Method.QUART.apply(
                        1 - ((float) (time - lastScroll)) / scrollDuration
                ))
        )));
        int total = component.getBoundingBox().height() - height;
        if (scrollVal > total) {
            scrollStart = total;
            scrollEnd = total;
            lastScroll = 0;
            scrollVal = total;
        }

        if (scrollVal <= 0) {
            scrollStart = 0;
            scrollEnd = 0;
            lastScroll = 0;
            scrollVal = 0;
        }
    }

    public void scroll(double amount) {
        scrollStart = scrollVal;
        scrollEnd = amount * 3 + scrollEnd;
        lastScroll = Util.getMeasuringTimeMs();
    }

    @Override
    public void postRender(MatrixStack matrices, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY) {
        if (component.shouldPostRender()) {
            component.postRender(matrices, renderBounds, vertical ? x : x - scrollVal, vertical ? y - scrollVal : y, mouseX, mouseY);
        }
    }

    @Override
    public boolean shouldPostRender() {
        return true;
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(width, height);
    }

    @Override
    public boolean charTyped(char key, int modifiers) {
        if (component.isSelected()) {
            return component.charTyped(key, modifiers);
        }
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (component.isSelected()) {
            return component.keyPressed(keyCode, scanCode, modifiers);
        }
        return false;
    }
}
