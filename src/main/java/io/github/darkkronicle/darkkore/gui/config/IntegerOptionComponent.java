package io.github.darkkronicle.darkkore.gui.config;

import io.github.darkkronicle.darkkore.config.options.IntegerOption;
import io.github.darkkronicle.darkkore.util.FluidText;
import io.github.darkkronicle.darkkore.util.StringUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class IntegerOptionComponent extends TextOptionComponent<Integer, IntegerOption> {

    public IntegerOptionComponent(Screen parent, IntegerOption option, int width) {
        super(parent, option, width);
    }

    @Override
    public Text getConfigTypeInfo() {
        if (option.getMin() != null && option.getMax() != null) {
            return new FluidText("§7§o" + String.format(StringUtil.translate("darkkore.optiontype.info.integerrange"), option.getMin(), option.getMax()));
        }
        return new FluidText("§7§o" + StringUtil.translate("darkkore.optiontype.info.integer"));
    }

    @Override
    public boolean isValid(String string) {
        try {
            int num = Integer.parseInt(string);
            return (option.getMax() == null || option.getMin() == null) || (num <= option.getMax() && num >= option.getMin());
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String getStringValue() {
        return String.valueOf(option.getValue());
    }

    @Override
    public void setValueFromString(String string) {
        try {
            option.setValue(Integer.valueOf(string));
        } catch (NumberFormatException e) {

        }
    }

}
