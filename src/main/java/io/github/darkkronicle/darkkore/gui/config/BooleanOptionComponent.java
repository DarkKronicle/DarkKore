package io.github.darkkronicle.darkkore.gui.config;

import io.github.darkkronicle.darkkore.config.options.BooleanOption;
import io.github.darkkronicle.darkkore.gui.components.Component;
import io.github.darkkronicle.darkkore.gui.components.ToggleComponent;
import io.github.darkkronicle.darkkore.util.Color;

public class BooleanOptionComponent extends OptionComponent<Boolean, BooleanOption> {

    private ToggleComponent activeComp;

    public BooleanOptionComponent(BooleanOption option, int width) {
        super(option, width, 20);
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
