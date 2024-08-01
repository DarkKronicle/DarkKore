package io.github.imide.darkkore_reborn.gui.config;

import io.github.imide.darkkore_reborn.colors.CommonColors;
import io.github.imide.darkkore_reborn.config.options.BooleanOption;
import io.github.imide.darkkore_reborn.gui.components.Component;
import io.github.imide.darkkore_reborn.gui.components.impl.ToggleComponent;
import io.github.imide.darkkore_reborn.util.Color;
import io.github.imide.darkkore_reborn.util.FluidText;
import io.github.imide.darkkore_reborn.util.StringUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class BooleanOptionComponent extends OptionComponent<Boolean, BooleanOption> {

    private ToggleComponent activeComp;

    public BooleanOptionComponent(Screen parent, BooleanOption option, int width) {
        super(parent, option, width, 20);
    }

    @Override
    public Text getConfigTypeInfo() {
        return new FluidText("§7§o" + StringUtil.translate("darkkore-reborn.optiontype.info.boolean"));
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
                parent, option.getValue(),
                150,
                14,
                CommonColors.getButtonColor(),
                CommonColors.getButtonHover(),
                bool -> {
                    option.setValue(bool);
                    onUpdate();
                }
        );
        return activeComp;
    }

}
