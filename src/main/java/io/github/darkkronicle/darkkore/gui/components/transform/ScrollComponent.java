package io.github.darkkronicle.darkkore.gui.components.transform;

import io.github.darkkronicle.darkkore.gui.components.Component;
import io.github.darkkronicle.darkkore.settings.DarkKoreConfig;
import io.github.darkkronicle.darkkore.util.*;
import io.github.darkkronicle.darkkore.util.render.ScissorsStack;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Util;

/**
 * An {@link OffsetComponent} that is used to scroll a child {@link Component}
 */
public class ScrollComponent extends OffsetComponent {

    /** The position where scroll animation starts */
    private double scrollStart = 0;
    /** The position where scroll animation ends */
    private double scrollEnd = 0;

    @Setter
    @Getter
    /** The actual scroll value */
    private int scrollVal = 0;

    /** Last time scroll happened. Uses {@link Util#getMeasuringTimeMs()} */
    private long lastScroll = 0;

    /** Scroll animation time */
    @Setter @Getter private int scrollDuration = 300;

    /** If the scroll should apply vertically or horizontally */
    @Getter private final boolean vertical;

    public ScrollComponent(Screen parent, Component component, int width, int height, boolean vertical) {
        super(parent, component, width, height);
        this.vertical = vertical;
        this.selectable = true;
    }

    /** {@inheritDoc} */
    @Override
    public int getXOffset() {
        if (!vertical) {
            return -scrollVal;
        }
        return 0;
    }

    /** {@inheritDoc} */
    @Override
    public int getYOffset() {
        if (vertical) {
            return -scrollVal;
        }
        return 0;
    }

    /**
     * {@inheritDoc}
     * <p> We override this here because we need to scissor the bounds
     * */
    @Override
    public void render(DrawContext context, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY) {
        // We have to override this to scissor properly
        renderBounds = new PositionedRectangle(x, y, width, height);
        updateScroll();
        // This makes it so we don't get weird overlays
        ScissorsStack.getInstance().push(renderBounds);
        ScissorsStack.getInstance().applyStack();
        super.render(context, renderBounds, x, y, mouseX, mouseY);
        // Allow for nested scissoring
        ScissorsStack.getInstance().pop();
        ScissorsStack.getInstance().applyStack();
    }

    /** {@inheritDoc} */
    @Override
    public boolean checkIfHovered(PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY) {
        return renderBounds.isPointIn(mouseX, mouseY);
    }

    /**
     * {@inheritDoc}
     * We change this here because scrolling just changes where the object is but not where it is rendering
     */
    @Override
    protected PositionedRectangle getRenderBounds(int x, int y) {
        Rectangle bound = getBoundingBox();
        return new PositionedRectangle(x, y, bound.width(), bound.height());
    }

    /** {@inheritDoc} */
    @Override
    public boolean mouseScrolled(int x, int y, int mouseX, int mouseY, double amount) {
        if (super.mouseScrolled(x, y, mouseX, mouseY, amount)) {
            return true;
        }
        scroll(amount * 30);
        return true;
    }

    /**
     * Updates the scroll position to smooth scroll.
     */
    public void updateScroll() {
        long time = Util.getMeasuringTimeMs();
        scrollVal = (int) (scrollStart + (
                (scrollEnd - scrollStart) * (1 - (EasingMethod.Method.QUART.apply(
                        1 - ((float) (time - lastScroll)) / scrollDuration
                ))
        )));

        int total = vertical ? component.getBoundingBox().height() - height : component.getBoundingBox().width() - width;

        // Bound checks
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

    /** {@inheritDoc} */
    public void scroll(double amount) {
        scrollStart = scrollVal;
        scrollEnd = (-1 * amount * DarkKoreConfig.getInstance().scrollScale.getValue()) + scrollEnd;
        lastScroll = Util.getMeasuringTimeMs();
    }

}
