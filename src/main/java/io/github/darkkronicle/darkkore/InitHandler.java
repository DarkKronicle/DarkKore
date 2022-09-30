package io.github.darkkronicle.darkkore;

import io.github.darkkronicle.darkkore.config.options.*;
import io.github.darkkronicle.darkkore.gui.config.*;
import io.github.darkkronicle.darkkore.hotkeys.BasicHotkey;
import io.github.darkkronicle.darkkore.hotkeys.HotkeyHandler;
import io.github.darkkronicle.darkkore.hotkeys.HotkeySettingsComponent;
import io.github.darkkronicle.darkkore.hotkeys.HotkeySettingsOption;
import io.github.darkkronicle.darkkore.settings.DarkKoreConfig;
import io.github.darkkronicle.darkkore.gui.ConfigScreen;
import io.github.darkkronicle.darkkore.gui.OptionComponentHolder;
import io.github.darkkronicle.darkkore.intialization.Initializer;
import net.minecraft.client.MinecraftClient;

import java.util.List;
import java.util.Optional;

public class InitHandler implements Initializer {

    @Override
    public void init() {

        HotkeyHandler.getInstance().add(
                DarkKore.MOD_ID,
                "settings", () ->
                        List.of(
                                new BasicHotkey(
                                        DarkKoreConfig.getInstance().openGui.getValue(),
                                        () -> MinecraftClient.getInstance().setScreen(DarkKoreConfig.getInstance().getScreen())
                                )
                        )
        );

        OptionComponentHolder.getInstance().addWithOrder(25, (parent, option, width) -> {
            if (option instanceof ColorOption) {
                return Optional.of(new ColorOptionComponent(parent, (ColorOption) option, width));
            }
            return Optional.empty();
        });
        OptionComponentHolder.getInstance().addWithOrder(10, (parent, option, width) -> {
            if (option instanceof ExtendedColorOption) {
                return Optional.of(new ExtendColorOptionComponent(parent, (ExtendedColorOption) option, width));
            }
            return Optional.empty();
        });
        OptionComponentHolder.getInstance().addWithOrder(100, (parent, option, width) -> {
            if (option instanceof BooleanOption) {
                return Optional.of(new BooleanOptionComponent(parent, (BooleanOption) option, width));
            }
            return Optional.empty();
        });
        OptionComponentHolder.getInstance().addWithOrder(100, (parent, option, width) -> {
            if (option instanceof StringOption) {
                return Optional.of(new StringOptionComponent(parent, (StringOption) option, width));
            }
            return Optional.empty();
        });
        OptionComponentHolder.getInstance().addWithOrder(50, (parent, option, width) -> {
            if (option instanceof DoubleOption) {
                return Optional.of(new DoubleOptionComponent(parent, (DoubleOption) option, width));
            }
            return Optional.empty();
        });
        OptionComponentHolder.getInstance().addWithOrder(50, (parent, option, width) -> {
            if (option instanceof IntegerOption) {
                return Optional.of(new IntegerOptionComponent(parent, (IntegerOption) option, width));
            }
            return Optional.empty();
        });
        OptionComponentHolder.getInstance().addWithOrder(100, (parent, option, width) -> {
            if (option instanceof ListOption<?>) {
                return Optional.of(new ListOptionComponent<>(parent, (ListOption<?>) option, width));
            }
            return Optional.empty();
        });
        OptionComponentHolder.getInstance().addWithOrder(100, (parent, option, width) -> {
            if (option instanceof HotkeySettingsOption) {
                return Optional.of(new HotkeySettingsComponent(parent, (HotkeySettingsOption) option, width));
            }
            return Optional.empty();
        });
        OptionComponentHolder.getInstance().addWithOrder(100, (parent, option, width) -> {
            if (option instanceof OptionSection) {
                return Optional.of(new OptionSectionComponent(parent, (OptionSection) option, width));
            }
            return Optional.empty();
        });
    }

}
