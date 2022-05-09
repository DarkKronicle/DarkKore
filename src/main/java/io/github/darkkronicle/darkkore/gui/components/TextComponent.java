package io.github.darkkronicle.darkkore.gui.components;

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

public class TextComponent extends BasicComponent {

    @Getter private List<Text> lines;

    @Getter @Setter private boolean updateBounds;

    @Getter @Setter private int width;

    @Getter @Setter private int height;

    @Getter @Setter private boolean autoUpdateWidth = false;

    @Getter @Setter private boolean autoUpdateHeight = false;


    @Getter @Setter private int leftPadding = 2;
    @Getter @Setter private int rightPadding = 2;
    @Getter @Setter private int topPadding = 2;
    @Getter @Setter private int bottomPadding = 2;
    @Getter @Setter private int linePadding = 2;


    public TextComponent(Text text) {
        this(-1, -1, text);
    }

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

    public void setLines(Text text) {
        text = StyleFormatter.formatText(new FluidText(text));
        this.lines = StyleFormatter.wrapText(MinecraftClient.getInstance().textRenderer, autoUpdateWidth ? 10000000 : width, text);
        if (autoUpdateWidth) {
            updateWidth();
        }
        if (autoUpdateHeight) {
            updateHeight();
        }
    }

    private void updateWidth() {
        int maxWidth = 0;
        for (Text text : lines) {
            maxWidth = Math.max(maxWidth, MinecraftClient.getInstance().textRenderer.getWidth(text) + leftPadding + rightPadding);
        }
        width = maxWidth;
    }

    private void updateHeight() {
        height = topPadding + bottomPadding + lines.size() * (MinecraftClient.getInstance().textRenderer.fontHeight + linePadding);
    }

    @Override
    public void renderComponent(MatrixStack matrices, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY) {
        int renderY = y + topPadding;
        for (Text line : lines) {
            MinecraftClient.getInstance().textRenderer.drawWithShadow(matrices, line, x + leftPadding, renderY + topPadding, -1);
            renderY += MinecraftClient.getInstance().textRenderer.fontHeight + linePadding;
        }
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(width, height);
    }
}
