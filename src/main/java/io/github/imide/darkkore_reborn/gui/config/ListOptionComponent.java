package io.github.imide.darkkore_reborn.gui.config;

import io.github.imide.darkkore_reborn.colors.CommonColors;
import io.github.imide.darkkore_reborn.config.options.ListOption;
import io.github.imide.darkkore_reborn.config.options.OptionListEntry;
import io.github.imide.darkkore_reborn.gui.components.Component;
import io.github.imide.darkkore_reborn.gui.components.impl.CycleComponent;
import io.github.imide.darkkore_reborn.gui.components.impl.TextComponent;
import io.github.imide.darkkore_reborn.util.Color;
import io.github.imide.darkkore_reborn.util.FluidText;
import io.github.imide.darkkore_reborn.util.StringUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ListOptionComponent<T extends OptionListEntry<T>> extends OptionComponent<T, ListOption<T>> {

    private CycleComponent<T> component;

    public ListOptionComponent(Screen parent, ListOption<T> option, int width) {
        super(parent, option, width, 20);
    }

    @Override
    public Text getConfigTypeInfo() {
        return new FluidText("§7§o" + StringUtil.translate("darkkore-reborn.optiontype.info.list"));
    }

    @Override
    public Component getMainComponent() {
        component = new CycleComponent<>(
                parent, option.getValue(),
                -1,
                14,
                CommonColors.getButtonColor(),
                CommonColors.getButtonHover(),
                value -> {
                    option.setValue(value);
                    onUpdate();
                }
        );
        if (component.getWidth() < 150) {
            component.setWidth(150);
        }
        return component;
    }

    @Override
    protected void createHover() {
        StringBuilder builder = new StringBuilder();
        builder.append(StringUtil.translate(option.getInfoKey()));
        for (T entry : option.getValue().getAll()) {
            builder.append("\n  §d").append(StringUtil.translate(entry.getDisplayKey())).append("§7 - §r").append(StringUtil.translate(entry.getInfoKey()));
        }
        FluidText fluid = new FluidText(builder.toString());
        fluid.append("\n").append(getConfigTypeInfo());
        TextComponent text = new TextComponent(parent, width - 2, -1, fluid);
        text.setLeftPadding(4);
        text.setRightPadding(4);
        text.setBackgroundColor(new Color(20, 20, 20, 255));
        text.setOutlineColor(new Color(76, 13, 127, 255));
        text.setZOffset(100);
        hoverComponent = text;
    }

    @Override
    public void setValue(T newValue) {
        component.setEntry(newValue);
    }

}
