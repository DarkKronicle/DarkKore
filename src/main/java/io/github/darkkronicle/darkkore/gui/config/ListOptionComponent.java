package io.github.darkkronicle.darkkore.gui.config;

import io.github.darkkronicle.darkkore.config.options.ListOption;
import io.github.darkkronicle.darkkore.config.options.OptionListEntry;
import io.github.darkkronicle.darkkore.gui.components.Component;
import io.github.darkkronicle.darkkore.gui.components.CycleComponent;
import io.github.darkkronicle.darkkore.gui.components.TextComponent;
import io.github.darkkronicle.darkkore.util.Color;
import io.github.darkkronicle.darkkore.util.FluidText;
import io.github.darkkronicle.darkkore.util.StringUtil;

public class ListOptionComponent<T extends OptionListEntry<T>> extends OptionComponent<T, ListOption<T>> {

    private CycleComponent<T> component;

    public ListOptionComponent(ListOption<T> option, int width) {
        super(option, width, 20);
    }

    @Override
    public Component getMainComponent() {
        component = new CycleComponent<>(
                option.getValue(),
                -1,
                14,
                new Color(100, 100, 100, 150),
                new Color(150, 150, 150, 150),
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
        TextComponent text = new TextComponent(width - 2, -1, new FluidText(builder.toString()));
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
