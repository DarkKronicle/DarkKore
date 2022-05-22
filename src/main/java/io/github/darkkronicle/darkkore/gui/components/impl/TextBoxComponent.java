package io.github.darkkronicle.darkkore.gui.components.impl;

import io.github.darkkronicle.darkkore.gui.components.BasicComponent;
import io.github.darkkronicle.darkkore.gui.elements.TextBox;
import io.github.darkkronicle.darkkore.util.Color;
import io.github.darkkronicle.darkkore.util.FluidText;
import io.github.darkkronicle.darkkore.util.PositionedRectangle;
import io.github.darkkronicle.darkkore.util.Rectangle;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * A component that wraps a {@link TextBox}
 */
public class TextBoxComponent extends BasicComponent {

    /** {@link TextBox} that handles text and typing */
    @Getter protected final TextBox textField;

    /** Width of the full component */
    protected int width;
    /** Height of the full component */
    protected int height;

    /** {@link Color} for when the text box is selected */
    @Getter @Setter protected Color selectedColor = new Color(255, 255, 255, 255);

    /** Right margin before the text box starts */
    @Getter @Setter protected int rightPadding = 2;
    /** Left margin before the text box starts */
    @Getter @Setter protected int leftPadding = 2;
    /** Top margin before the text box starts */
    @Getter @Setter protected int topPadding = 2;
    /** Bottom margin before the text box starts */
    @Getter @Setter protected int bottomPadding = 2;

    /**
     * Creates a new text box component
     * @param message The starting message in the text box.
     * @param width Width of the box
     * @param height Height of the box
     * @param changedListener {@link Consumer} for when the text is changed
     */
    public TextBoxComponent(@Nullable  String message, int width, int height, Consumer<String> changedListener) {
        this.width = width;
        this.height = height;
        textField = new TextBox(MinecraftClient.getInstance().textRenderer, 0, 0, width - leftPadding - rightPadding, height - topPadding - bottomPadding, new FluidText());
        textField.setDrawsBackground(false);
        textField.setMaxLength(128000);
        selectable = true;
        if (message != null) {
            setText(message);
        }
        if (changedListener != null) {
            textField.setChangedListener(changedListener);
        }
    }

    /**
     * Updates width and height for the text box. Useful for {@link #rightPadding} and other padding options.
     */
    public void update() {
        textField.setWidth(width - rightPadding - leftPadding);
        textField.setHeight(height - topPadding - bottomPadding);
    }

    /** Sets the {@link Consumer} for when the text box's contents are modified */
    public void setChangedListener(Consumer<String> changedListener) {
        textField.setChangedListener(changedListener);
    }

    /**
     * Gets the text box's current text
     * @return Text box's current text
     */
    public String getText() {
        return textField.getText();
    }

    /**
     * Sets the text box's text
     * @param text New text that can be set
     */
    public void setText(String text) {
        textField.setText(text);
    }

    /** {@inheritDoc} */
    @Override
    public void renderComponent(MatrixStack matrices, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY) {
        textField.x = x + 3;
        textField.y = y + 3;
        textField.render(matrices, mouseX, mouseY, 0);
    }

    /** {@inheritDoc} */
    @Override
    public boolean charTyped(char key, int modifiers) {
        return textField.charTyped(key, modifiers);
    }

    /** {@inheritDoc} */
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return textField.keyPressed(keyCode, scanCode, modifiers);
    }

    /** {@inheritDoc} */
    @Override
    public void mouseClickedOutsideImpl(int x, int y, int mouseX, int mouseY, int button) {
        textField.setTextFieldFocused(false);
    }

    /** {@inheritDoc} */
    @Override
    public boolean mouseClickedImpl(int x, int y, int mouseX, int mouseY, int button) {
        textField.setTextFieldFocused(true);
        textField.mouseClicked(mouseX, mouseY, 0);
        return true;
    }

    /**
     * {@inheritDoc}
     * <p>Sets the outline when selected
     */
    @Override
    public void onSelectedImpl(boolean selected) {
        if (selected) {
            setOutlineColor(selectedColor);
        } else {
            setOutlineColor(null);
        }
    }

    /** {@inheritDoc} */
    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(width, height);
    }

    /** {@inheritDoc} */
    @Override
    public void setSelected(boolean value) {
        super.setSelected(value);
        textField.setTextFieldFocused(value);
    }
}
