package io.github.darkkronicle.darkkore.gui.config;

import io.github.darkkronicle.darkkore.config.options.StringOption;
import io.github.darkkronicle.darkkore.gui.components.Component;
import io.github.darkkronicle.darkkore.gui.components.impl.TextBoxComponent;
import io.github.darkkronicle.darkkore.util.Color;
import io.github.darkkronicle.darkkore.util.FluidText;
import io.github.darkkronicle.darkkore.util.StringUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class StringOptionComponent extends TextOptionComponent<String, StringOption> {

    private TextBoxComponent textBox;

    public StringOptionComponent(Screen parent, StringOption option, int width) {
        super(parent, option, width);
        selectable = true;
    }

    @Override
    public boolean isValid(String string) {
        if (option.getCheck() != null) {
            return option.getCheck().test(string);
        }
        return true;
    }

    @Override
    public String getStringValue() {
        return option.getValue();
    }

    @Override
    public void setValueFromString(String string) {
        option.setValue(string);
    }

    @Override
    public Text getConfigTypeInfo() {
        if (option.getTypeKey() != null) {
            return new FluidText("§7§o" + StringUtil.translate(option.getTypeKey()));
        }
        return new FluidText("§7§o" + StringUtil.translate("darkkore.optiontype.info.string"));
    }

    @Override
    public Component getMainComponent() {
        textBox = new TextBoxComponent(parent, option.getValue(),150, 14, this::onChanged);
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
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (textBox.isSelected()) {
            return textBox.keyPressed(keyCode, scanCode, modifiers);
        }
        return false;
    }
}
