package io.github.darkkronicle.darkkore.gui.config;

import io.github.darkkronicle.darkkore.colors.CommonColors;
import io.github.darkkronicle.darkkore.config.options.Option;
import io.github.darkkronicle.darkkore.gui.components.Component;
import io.github.darkkronicle.darkkore.gui.components.impl.TextBoxComponent;
import io.github.darkkronicle.darkkore.util.Color;
import net.minecraft.client.gui.screen.Screen;

public abstract class TextOptionComponent<N, T extends Option<N>> extends OptionComponent<N, T> {

    private TextBoxComponent textBox;

    protected boolean failure = false;

    public TextOptionComponent(Screen parent, T option, int width) {
        super(parent, option, width, 20);
    }

    public abstract boolean isValid(String string);

    public abstract String getStringValue();

    public abstract void setValueFromString(String string);

    protected void onChanged(String string) {
        if (isValid(string)) {
            setBackgroundColor(new Color(50, 50, 50, 150));
            setValueFromString(string);
            onUpdate();
            failure = false;
        } else {
            failure = true;
            setBackgroundColor(new Color(170, 50, 50, 150));
        }
    }

    @Override
    public void onHovered(int x, int y, int mouseX, int mouseY, boolean hovered) {
        super.onHovered(x, y, mouseX, mouseY, hovered);
        if (hovered) {
            setBackgroundColor(CommonColors.getOptionBackgroundHover());
        } else {
            setBackgroundColor(null);
        }
        if (failure) {
            setBackgroundColor(new Color(170, 50, 50, 150));
        }
    }

    @Override
    public Component getMainComponent() {
        textBox = new TextBoxComponent(parent, getStringValue(),150, 14, this::onChanged);
        textBox.setBackgroundColor(new Color(50, 50, 50, 150));
        return textBox;
    }

    @Override
    public boolean charTyped(char key, int modifiers) {
        if (textBox.isSelected()) {
            return textBox.charTyped(key, modifiers);
        }
        return false;
    }

    @Override
    public void setValue(N newValue) {
        option.setValue(newValue);
        textBox.setText(getStringValue());
        onUpdate();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (textBox.isSelected()) {
            return textBox.keyPressed(keyCode, scanCode, modifiers);
        }
        return false;
    }

}
