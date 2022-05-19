package io.github.darkkronicle.darkkore;

import io.github.darkkronicle.darkkore.gui.ComponentScreen;
import io.github.darkkronicle.darkkore.gui.components.impl.TextComponent;
import io.github.darkkronicle.darkkore.gui.components.transform.ListComponent;
import io.github.darkkronicle.darkkore.gui.components.transform.PositionedComponent;
import io.github.darkkronicle.darkkore.gui.components.transform.ScrollComponent;
import io.github.darkkronicle.darkkore.util.FluidText;

public class TestScreen extends ComponentScreen {

    @Override
    public void initImpl() {
        ListComponent list = new ListComponent(200, -1, false);
        for (int i = 0; i < 500; i++) {
            list.addComponent(new TextComponent(new FluidText(i + " is cool!")));
        }
        addComponent(new PositionedComponent(
                new ScrollComponent(
                        list, 200, 400, true
                ),
                10,
                10,
                200,
                400
        ));
    }
}
