package io.github.darkkronicle.darkkore.gui.config;

import io.github.darkkronicle.darkkore.config.options.BooleanOption;
import io.github.darkkronicle.darkkore.gui.components.PositionedComponent;
import io.github.darkkronicle.darkkore.gui.components.TextComponent;
import io.github.darkkronicle.darkkore.gui.components.ToggleComponent;
import io.github.darkkronicle.darkkore.util.Color;
import io.github.darkkronicle.darkkore.util.Dimensions;
import io.github.darkkronicle.darkkore.util.FluidText;
import io.github.darkkronicle.darkkore.util.StringUtil;

public class BooleanOptionComponent extends OptionComponent<Boolean, BooleanOption> {


    public BooleanOptionComponent(BooleanOption option, int width) {
        super(option, width, 20);
    }

    @Override
    public void addComponents(Dimensions bounds) {
        TextComponent nameComp = new TextComponent(bounds.getWidth() - 160, -1, new FluidText(StringUtil.translate(option.getDisplayNameKey())));
        addComponent(
                new PositionedComponent(
                    nameComp,
                    4,
                    3,
                    nameComp.getBoundingBox().width(),
                    nameComp.getBoundingBox().height()
                )
        );
        setHeight(nameComp.getHeight() + 6);
        ToggleComponent activeComp = new ToggleComponent(
                option.getValue(),
                150,
                14,
                new Color(100, 100, 100, 150),
                new Color(150, 150, 150, 150),
                option::setValue
        );
        addComponent(
                new PositionedComponent(
                        activeComp,
                        bounds.getWidth() - activeComp.getWidth() - 8,
                        3,
                        activeComp.getBoundingBox().width(),
                        activeComp.getBoundingBox().height()
                )
        );
    }

}
