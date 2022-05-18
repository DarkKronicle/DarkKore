package io.github.darkkronicle.darkkore.gui.config;

import io.github.darkkronicle.darkkore.config.options.BooleanOption;
import io.github.darkkronicle.darkkore.gui.components.Component;
import io.github.darkkronicle.darkkore.gui.components.impl.ToggleComponent;
import io.github.darkkronicle.darkkore.util.Color;
import io.github.darkkronicle.darkkore.util.FluidText;
import io.github.darkkronicle.darkkore.util.StringUtil;
import net.minecraft.text.Text;

public class BooleanOptionComponent extends OptionComponent<Boolean, BooleanOption> {

    private ToggleComponent activeComp;

    public BooleanOptionComponent(BooleanOption option, int width) {
        super(option, width, 20);
    }

    @Override
    public Text getConfigTypeInfo() {
        return new FluidText("§7§o" + StringUtil.translate("darkkore.optiontype.info.boolean"));
    }

    @Override
    public void setValue(Boolean newValue) {
        activeComp.setValue(newValue);
        activeComp.setLines(activeComp.getFullText());
        onUpdate();
    }

    @Override
    public Component getMainComponent() {
        activeComp = new ToggleComponent(
                option.getValue(),
                150,
                14,
                new Color(100, 100, 100, 150),
                new Color(150, 150, 150, 150),
                bool -> {
                    option.setValue(bool);
                    onUpdate();
                }
        );
        return activeComp;
    }

}
