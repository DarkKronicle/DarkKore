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

import java.util.function.Consumer;

public class TextBoxComponent extends BasicComponent {

    @Getter private final TextBox textField;
    protected int width;
    protected int height;

    @Getter @Setter protected int rightPadding = 2;
    @Getter @Setter protected int leftPadding = 2;

    @Getter @Setter protected int topPadding = 2;
    @Getter @Setter protected int bottomPadding = 2;

    public TextBoxComponent(String message, int width, int height, Consumer<String> changedListener) {
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

    public void update() {
        textField.setWidth(width - rightPadding - leftPadding);
        textField.setHeight(height - topPadding - bottomPadding);
    }

    public void setChangedListener(Consumer<String> changedListener) {
        textField.setChangedListener(changedListener);
    }

    public String getText() {
        return textField.getText();
    }

    public void setText(String text) {
        textField.setText(text);
    }

    @Override
    public void renderComponent(MatrixStack matrices, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY) {
        textField.x = x + 3;
        textField.y = y + 3;
        textField.render(matrices, mouseX, mouseY, 0);
    }

    @Override
    public boolean charTyped(char key, int modifiers) {
        return textField.charTyped(key, modifiers);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return textField.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void mouseClickedOutside(int x, int y, int mouseX, int mouseY) {
        super.mouseClickedOutside(x, y, mouseX, mouseY);
        textField.setTextFieldFocused(false);
    }

    @Override
    public boolean mouseClicked(int x, int y, int mouseX, int mouseY, int button) {
        super.mouseClicked(x, y, mouseX, mouseY, button);
        textField.setTextFieldFocused(true);
        textField.mouseClicked(mouseX, mouseY, 0);
        return true;
    }

    @Override
    public void onSelectedImpl(boolean selected) {
        if (selected) {
            setOutlineColor(new Color(255, 255, 255, 255));
        } else {
            setOutlineColor(null);
        }
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(width, height);
    }

}
