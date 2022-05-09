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
    }

    @Override
    public void renderComponent(MatrixStack matrices, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY) {
        updateScroll();
        renderBounds = new PositionedRectangle(x, y, width, height);
        ScissorUtil.applyScissor(renderBounds);
        component.render(matrices, renderBounds, vertical ? x : x - scrollVal, vertical ? y - scrollVal : y, mouseX, mouseY);
        ScissorUtil.resetScissor();
    }

    @Override
    public boolean mouseClicked(int x, int y, int mouseX, int mouseY) {
        return component.mouseClicked(vertical ? x : x - scrollVal, vertical ? y - scrollVal : y, mouseX, mouseY);
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
        scrollEnd = amount * 3 + scrollStart;
        lastScroll = Util.getMeasuringTimeMs();
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(width, height);
    }

}
