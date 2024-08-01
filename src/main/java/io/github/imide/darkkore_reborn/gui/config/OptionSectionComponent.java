package io.github.imide.darkkore_reborn.gui.config;

import io.github.imide.darkkore_reborn.colors.CommonColors;
import io.github.imide.darkkore_reborn.config.options.Option;
import io.github.imide.darkkore_reborn.config.options.OptionSection;
import io.github.imide.darkkore_reborn.gui.ConfigScreen;
import io.github.imide.darkkore_reborn.gui.components.Component;
import io.github.imide.darkkore_reborn.gui.components.impl.ButtonComponent;
import io.github.imide.darkkore_reborn.util.FluidText;
import io.github.imide.darkkore_reborn.util.StringUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.List;

public class OptionSectionComponent extends OptionComponent<List<Option<?>>, OptionSection>{

    public OptionSectionComponent(Screen parent, OptionSection option, int width) {
        super(parent, option, width, 18);
    }

    @Override
    public Text getConfigTypeInfo() {
        return new FluidText("§7§o" + StringUtil.translate("darkkore-reborn.optiontype.info.optionsection"));
    }

    @Override
    public void setValue(List<Option<?>> newValue) {
        option.setValue(newValue);
    }

    @Override
    public Component getMainComponent() {
        ButtonComponent configure = new ButtonComponent(parent, StringUtil.translateToText("darkkore-reborn.button.configure"), CommonColors.getButtonColor(), CommonColors.getButtonHover(), (button) -> {
            ConfigScreen screen = ConfigScreen.of(option.getValue());
            screen.setParent(parent);
            MinecraftClient.getInstance().setScreen(screen);
        });
        configure.setHeight(14);
        return configure;
    }
}
