package io.github.darkkronicle.darkkore.gui;

import io.github.darkkronicle.darkkore.config.options.Option;
import io.github.darkkronicle.darkkore.gui.components.transform.ListComponent;
import io.github.darkkronicle.darkkore.gui.components.transform.PositionedComponent;
import io.github.darkkronicle.darkkore.gui.components.transform.ScrollComponent;
import io.github.darkkronicle.darkkore.gui.config.OptionComponent;
import io.github.darkkronicle.darkkore.util.Color;
import io.github.darkkronicle.darkkore.util.Dimensions;

import java.util.List;

public class ConfigScreen extends ComponentScreen {

    private List<Option<?>> options = null;

    protected int yDist = 12;

    public ConfigScreen() {

    }

    public ConfigScreen(List<Option<?>> options) {
        this.options = options;
    }

    public List<Option<?>> getOptions() {
        return options;
    }

    @Override
    public void initImpl() {
        Dimensions dimensions = Dimensions.getScreen();
        int width = dimensions.getWidth() - 20;
        ListComponent list = new ListComponent(width, -1, true);
        for (Option<?> option : options) {
            OptionComponent<?, ?> component = OptionComponentHolder.getInstance().convert(option, width - 2);
            if (component == null) {
                continue;
            }
            component.setOutlineColor(new Color(100, 100, 100, 100));
            list.addComponent(component);
        }
        addComponent(
                new PositionedComponent(
                        new ScrollComponent(list, width, dimensions.getHeight() - 20, true),
                        10, 20, width, dimensions.getHeight() - 30).setOutlineColor(new Color(200, 200, 200, 200))
        );
    }
}
