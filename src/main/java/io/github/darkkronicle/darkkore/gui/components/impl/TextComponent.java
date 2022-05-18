package io.github.darkkronicle.darkkore.gui.components.impl;

import io.github.darkkronicle.darkkore.gui.components.BasicComponent;
import io.github.darkkronicle.darkkore.util.FluidText;
import io.github.darkkronicle.darkkore.util.PositionedRectangle;
import io.github.darkkronicle.darkkore.util.Rectangle;
import io.github.darkkronicle.darkkore.util.StyleFormatter;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.List;

/**
 * A {@link io.github.darkkronicle.darkkore.gui.components.Component} to display text.
 *
 * <p>This automatically sets width/height and supports new line characters
 */
public class TextComponent extends BasicComponent {

    /** The lines that will render */
    @Getter protected List<Text> lines;

    /** The width of the box */
    @Getter @Setter protected int width;

    /** The height of the box */
    @Getter @Setter protected int height;

    /** Should the width be set automatically */
    @Getter @Setter protected boolean autoUpdateWidth = false;

    /** Should the height be set automatically */
    @Getter @Setter protected boolean autoUpdateHeight = false;

    /** Should this render from the center */
    @Getter @Setter protected boolean center = false;

    /** Left margin */
    @Getter @Setter protected int leftPadding = 2;
    /** Right margin */
    @Getter @Setter protected int rightPadding = 2;
    /** Top margin */
    @Getter @Setter protected int topPadding = 2;
    /** Bottom margin */
    @Getter @Setter protected int bottomPadding = 2;
    /** Line margin */
    @Getter @Setter protected int linePadding = 2;


    /**
     * Creates a new object and sets the width and height automatically.
     * @param text {@link Text} to display. Supports new lines.
     */
    public TextComponent(Text text) {
        this(-1, -1, text);
    }

    /**
     * Creates a new object with a specified width and height. If width/height are less than 0
     * then {@link #autoUpdateWidth}/{@link #autoUpdateHeight} are set.
     * @param width Width of the object. Can be less than 0.
     * @param height Height of the object. Can be less than 0.
     * @param text {@link Text} to display. Supports new lines.
     */
    public TextComponent(int width, int height, Text text) {
        this.width = width;
        this.height = height;
        if (width < 0) {
            autoUpdateWidth = true;
        }
        if (height < 0) {
            autoUpdateHeight = true;
        }
        setLines(text);
    }

    /**
     * Set the lines from a {@link Text} and automatically wrap the text
     * @param text {@link Text} to set
     */
    public void setLines(Text text) {
        // We format the text fancily
        text = StyleFormatter.formatText(new FluidText(text));
        // We wrap the text
        this.lines = StyleFormatter.wrapText(MinecraftClient.getInstance().textRenderer, autoUpdateWidth ? 10000000 : width, text);
        if (autoUpdateWidth) {
            updateWidth();
        }
        if (autoUpdateHeight) {
            updateHeight();
        }
    }

    /**
     * Updates the width to the longest line
     */
    protected void updateWidth() {
        int maxWidth = 0;
        for (Text text : lines) {
            maxWidth = Math.max(maxWidth, MinecraftClient.getInstance().textRenderer.getWidth(text) + leftPadding + rightPadding);
        }
        width = maxWidth;
    }

    /**
     * Updates the height to the amount of lines
     */
    protected void updateHeight() {
        height = topPadding + bottomPadding + lines.size() * (MinecraftClient.getInstance().textRenderer.fontHeight + linePadding);
    }

    /** {@inheritDoc} */
    @Override
    public void renderComponent(MatrixStack matrices, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY) {
        int renderY = y + topPadding;
        for (Text line : lines) {
            if (center) {
                // To center we get the middle value then subtract half the width
                int width = MinecraftClient.getInstance().textRenderer.getWidth(line);
                MinecraftClient.getInstance().textRenderer.drawWithShadow(
                        matrices, line,
                        x + leftPadding + (int) ((this.width - rightPadding) / 2) - (int) (width / 2),
                        renderY + topPadding, -1
                );
            } else {
                MinecraftClient.getInstance().textRenderer.drawWithShadow(matrices, line, x + leftPadding, renderY + topPadding, -1);
            }
            renderY += MinecraftClient.getInstance().textRenderer.fontHeight + linePadding;
        }
    }

    /** {@inheritDoc} */
    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(width, height);
    }
}
