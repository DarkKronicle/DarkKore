package io.github.darkkronicle.darkkore.gui.config;

import io.github.darkkronicle.darkkore.DarkKore;
import io.github.darkkronicle.darkkore.config.options.IconButtonComponent;
import io.github.darkkronicle.darkkore.gui.components.ButtonComponent;
import io.github.darkkronicle.darkkore.util.Color;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class ResetButtonComponent extends IconButtonComponent {

    private static final Identifier ADD_ICON = new Identifier(DarkKore.MOD_ID, "textures/gui/icons/refresh.png");

    public ResetButtonComponent(int size, Color background, Color hover, Consumer<ButtonComponent> onClick) {
        super(null, size, size, 48, 48, background, hover, onClick);
        topPadding = 0;
        bottomPadding = 0;
        leftPadding = 0;
        rightPadding = 0;
        setIcon(ADD_ICON);
    }

    public void disable() {
        setDisabled(true);
        setShaderColor(new Color(170, 170, 170, 255));
    }

    public void enable() {
        setDisabled(false);
        setShaderColor(new Color(255, 255, 255, 255));
    }

}
