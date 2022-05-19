package io.github.darkkronicle.darkkore.gui.config;

import io.github.darkkronicle.darkkore.config.options.DoubleOption;
import io.github.darkkronicle.darkkore.config.options.IntegerOption;
import io.github.darkkronicle.darkkore.util.FluidText;
import io.github.darkkronicle.darkkore.util.StringUtil;
import net.minecraft.text.Text;

public class DoubleOptionComponent extends TextOptionComponent<Double, DoubleOption> {

    public DoubleOptionComponent(DoubleOption option, int width) {
        super(option, width);
    }

    @Override
    public Text getConfigTypeInfo() {
        if (option.getMin() != null && option.getMax() != null) {
            return new FluidText("§7§o" + String.format(StringUtil.translate("darkkore.optiontype.info.doublerange"), option.getMin(), option.getMax()));
        }
        return new FluidText("§7§o" + StringUtil.translate("darkkore.optiontype.info.double"));
    }

    @Override
    public boolean isValid(String string) {
        try {
            double num = Double.parseDouble(string);
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
            option.setValue(Double.valueOf(string));
        } catch (NumberFormatException e) {

        }
    }

}
