package io.github.imide.darkkore_reborn.hotkeys;

import io.github.imide.darkkore_reborn.colors.CommonColors;
import io.github.imide.darkkore_reborn.config.options.BooleanOption;
import io.github.imide.darkkore_reborn.config.options.Option;
import io.github.imide.darkkore_reborn.gui.ConfigScreen;
import io.github.imide.darkkore_reborn.gui.components.Component;
import io.github.imide.darkkore_reborn.gui.components.impl.ButtonComponent;
import io.github.imide.darkkore_reborn.gui.components.transform.ListComponent;
import io.github.imide.darkkore_reborn.gui.config.OptionComponent;
import io.github.imide.darkkore_reborn.gui.config.SettingsButtonComponent;
import io.github.imide.darkkore_reborn.intialization.profiles.PlayerContextOption;
import io.github.imide.darkkore_reborn.util.FluidText;
import io.github.imide.darkkore_reborn.util.StringUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class HotkeySettingsComponent extends OptionComponent<HotkeySettings, HotkeySettingsOption> {

    private HotkeyComponent component;

    public HotkeySettingsComponent(Screen parent, HotkeySettingsOption option, int width) {
        super(parent, option, width, 20);
    }

    @Override
    public Text getConfigTypeInfo() {
        return new FluidText("§7§o" + StringUtil.translate("darkkore-reborn.optiontype.info.hotkey"));
    }

    @Override
    public void setValue(HotkeySettings newValue) {
        component.setKeys(newValue.getKeys());
        onUpdate();
    }

    @Override
    public Component getMainComponent() {
        component = new HotkeyComponent(
                parent,
                option.getValue().getKeys(),
                132,
                14,
                CommonColors.getButtonColor(),
                CommonColors.getButtonHover()
        );
        ButtonComponent settings = new SettingsButtonComponent(
                parent,
                14,
                CommonColors.getButtonColor(),
                CommonColors.getButtonHover(),
                button -> {
                    ConfigScreen screen = ConfigScreen.of(getDetailedOptions());
                    screen.setParent(parent);
                    MinecraftClient.getInstance().setScreen(screen);
                }
        );
        ListComponent list = new ListComponent(parent, -1, -1, false);
        list.setTopPad(0);
        list.setRightPad(0);
        list.addComponent(component);
        list.addComponent(settings);
        return list;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        option.getValue().setKeys(component.getKeys());
    }

    public List<Option<?>> getDetailedOptions() {
        BooleanOption blocking = new BooleanOption(
                "blocking",
                "darkkore-reborn.option.hotkey.blocking",
                "darkkore-reborn.option.hotkey.info.blocking",
                getOption().getDefaultValue().isBlocking()
        ) {
            @Override
            public void setValue(Boolean value) {
                super.setValue(value);
                getOption().getValue().setBlocking(value);
            }
        };
        blocking.setValue(getOption().getValue().isBlocking());
        BooleanOption exclusive = new BooleanOption(
                "exclusive",
                "darkkore-reborn.option.hotkey.exclusive",
                "darkkore-reborn.option.hotkey.info.exclusive",
                getOption().getDefaultValue().isExclusive()
        ) {
            @Override
            public void setValue(Boolean value) {
                super.setValue(value);
                getOption().getValue().setExclusive(value);
            }
        };
        exclusive.setValue(getOption().getValue().isExclusive());
        BooleanOption ordered = new BooleanOption(
                "ordered",
                "darkkore-reborn.option.hotkey.ordered",
                "darkkore-reborn.option.hotkey.info.ordered",
                getOption().getDefaultValue().isOrdered()
        ) {
            @Override
            public void setValue(Boolean value) {
                super.setValue(value);
                getOption().getValue().setOrdered(value);
            }
        };
        ordered.setValue(getOption().getValue().isOrdered());
        List<Option<?>> opt = new ArrayList<>(List.of(blocking, exclusive, ordered));
        opt.addAll(PlayerContextOption.getOptions(getOption().getDefaultValue().getCheck(), getOption().getValue().getCheck()));
        return opt;
    }

}
