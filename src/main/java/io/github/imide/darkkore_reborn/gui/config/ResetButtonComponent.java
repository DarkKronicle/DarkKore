package io.github.imide.darkkore_reborn.gui.config;

import io.github.imide.darkkore_reborn.DarkKore_reborn;
import io.github.imide.darkkore_reborn.gui.components.impl.ButtonComponent;
import io.github.imide.darkkore_reborn.gui.components.impl.IconButtonComponent;
import io.github.imide.darkkore_reborn.util.Color;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class ResetButtonComponent extends IconButtonComponent {

    private static final Identifier ADD_ICON = Identifier.of(DarkKore_reborn.MOD_ID, "textures/gui/icons/refresh.png");

    public ResetButtonComponent(Screen parent, int size, Color background, Color hover, Consumer<ButtonComponent> onClick) {
        super(parent, null, size, size, 48, 48, background, hover, onClick);
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
