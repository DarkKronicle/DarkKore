package io.github.darkkronicle.darkkore.gui.elements;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

/**
 * An extension of {@link TextFieldWidget} to provide more customization
 */
public class TextBox extends TextFieldWidget {

    public TextBox(TextRenderer textRenderer, int x, int y, int width, int height, Text text) {
        super(textRenderer, x, y, width, height, text);
    }

    public TextBox(TextRenderer textRenderer, int x, int y, int width, int height, @Nullable TextFieldWidget copyFrom, Text text) {
        super(textRenderer, x, y, width, height, copyFrom, text);
    }

    /**
     * Sets the width
     * @param width Width
     */
    @Override
    public void setWidth(int width) {
        super.setWidth(width);
    }

    /**
     * Sets the height
     * @param height Height
     */
    public void setHeight(int height) {
        this.height = height;
    }

}
