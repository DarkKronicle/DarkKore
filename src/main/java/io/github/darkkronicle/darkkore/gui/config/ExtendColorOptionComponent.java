package io.github.darkkronicle.darkkore.gui.config;

import io.github.darkkronicle.darkkore.colors.CommonColors;
import io.github.darkkronicle.darkkore.config.options.ColorOption;
import io.github.darkkronicle.darkkore.config.options.ExtendedColorOption;
import io.github.darkkronicle.darkkore.gui.ConfigScreen;
import io.github.darkkronicle.darkkore.gui.components.impl.ButtonComponent;
import io.github.darkkronicle.darkkore.gui.components.transform.ListComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

public class ExtendColorOptionComponent extends ColorOptionComponent {

    public ExtendColorOptionComponent(
            Screen parent, ExtendedColorOption option, int width
    ) {
        super(parent, option, width);
    }

    @Override
    public ExtendedColorOption getOption() {
        // Has to be an extended color option
        return (ExtendedColorOption) super.getOption();
    }

    @Override
    public ListComponent getMainComponent() {
        ListComponent list = super.getMainComponent();
        // This **has** to be an extended color option
        ExtendedColorOption option = getOption();
        if (!option.anyExtended()) {
            return list;
        }
        ButtonComponent settings = new SettingsButtonComponent(
                parent,
                14,
                CommonColors.getButtonColor(),
                CommonColors.getButtonHover(),
                button -> {
                    ConfigScreen screen = ConfigScreen.ofSections(option.getNestedSections());
                    screen.setParent(parent);
                    MinecraftClient.getInstance().setScreen(screen);
                }
        );
        list.addComponent(settings);
        return list;
    }
}
