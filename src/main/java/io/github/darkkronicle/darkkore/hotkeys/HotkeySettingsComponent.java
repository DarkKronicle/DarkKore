package io.github.darkkronicle.darkkore.hotkeys;

import io.github.darkkronicle.darkkore.config.options.BooleanOption;
import io.github.darkkronicle.darkkore.config.options.IntegerOption;
import io.github.darkkronicle.darkkore.config.options.Option;
import io.github.darkkronicle.darkkore.config.options.StringOption;
import io.github.darkkronicle.darkkore.gui.ConfigScreen;
import io.github.darkkronicle.darkkore.gui.components.Component;
import io.github.darkkronicle.darkkore.gui.components.impl.ButtonComponent;
import io.github.darkkronicle.darkkore.gui.components.transform.ListComponent;
import io.github.darkkronicle.darkkore.gui.components.transform.MultiComponent;
import io.github.darkkronicle.darkkore.gui.config.OptionComponent;
import io.github.darkkronicle.darkkore.gui.config.SettingsButtonComponent;
import io.github.darkkronicle.darkkore.util.Color;
import io.github.darkkronicle.darkkore.util.FluidText;
import io.github.darkkronicle.darkkore.util.StringUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;

import java.util.List;

public class HotkeySettingsComponent extends OptionComponent<HotkeySettings, HotkeySettingsOption> {

    private HotkeyComponent component;

    public HotkeySettingsComponent(Screen parent, HotkeySettingsOption option, int width) {
        super(parent, option, width, 20);
    }

    @Override
    public Text getConfigTypeInfo() {
        return new FluidText("§7§o" + StringUtil.translate("darkkore.optiontype.info.hotkey"));
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
                new Color(100, 100, 100, 150),
                new Color(150, 150, 150, 150)
        );
        ButtonComponent settings = new SettingsButtonComponent(
                parent,
                14,
                new Color(100, 100, 100, 150),
                new Color(150, 150, 150, 150),
                button -> {
                    ConfigScreen screen = new ConfigScreen(getDetailedOptions());
                    screen.setParent(parent);
                    MinecraftClient.getInstance().setScreen(screen);
                }
        );
        ListComponent list = new ListComponent(-1, parent, -1, false);
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
                "darkkore.option.hotkey.blocking",
                "darkkore.option.hotkey.info.blocking",
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
                "darkkore.option.hotkey.exclusive",
                "darkkore.option.hotkey.info.exclusive",
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
                "darkkore.option.hotkey.ordered",
                "darkkore.option.hotkey.info.ordered",
                getOption().getDefaultValue().isOrdered()
        ) {
            @Override
            public void setValue(Boolean value) {
                super.setValue(value);
                getOption().getValue().setOrdered(value);
            }
        };
        ordered.setValue(getOption().getValue().isOrdered());
        BooleanOption inGame = new BooleanOption(
                "inGame",
                "darkkore.option.hotkey.ingame",
                "darkkore.option.hotkey.info.ingame",
                getOption().getDefaultValue().getCheck().getInGame()
        ) {
            @Override
            public void setValue(Boolean value) {
                super.setValue(value);
                getOption().getValue().getCheck().setInGame(value);
            }
        };
        inGame.setValue(getOption().getValue().getCheck().getInGame());
        BooleanOption inGui = new BooleanOption(
                "inGui",
                "darkkore.option.hotkey.ingui",
                "darkkore.option.hotkey.info.ingui",
                getOption().getDefaultValue().getCheck().getInGui()
        ) {
            @Override
            public void setValue(Boolean value) {
                super.setValue(value);
                getOption().getValue().getCheck().setInGui(value);
            }
        };
        inGui.setValue(getOption().getValue().getCheck().getInGui());
        StringOption server = new StringOption(
                "server",
                "darkkore.option.hotkey.server",
                "darkkore.option.hotkey.info.server",
                getOption().getDefaultValue().getCheck().getServer() == null ? "" : getOption().getDefaultValue().getCheck().getServer()
        ) {
            @Override
            public void setValue(String value) {
                super.setValue(value);
                if (value.isEmpty()) {
                    getOption().getValue().getCheck().setServer(null);
                } else {
                    getOption().getValue().getCheck().setServer(value);
                }
            }
        };
        if (getOption().getValue().getCheck().getServer() == null) {
            server.setValue("");
        } else {
            server.setValue(getOption().getValue().getCheck().getServer());
        }
        IntegerOption gameMode = new IntegerOption(
                "gameMode",
                "darkkore.option.hotkey.gamemode",
                "darkkore.option.hotkey.info.gamemode",
                getOption().getDefaultValue().getCheck().getGameMode() == null ? -1 : getOption().getDefaultValue().getCheck().getGameMode().getId(),
                -1,
                3
        ) {
            @Override
            public void setValue(Integer value) {
                super.setValue(value);
                if (value != null) {
                    // Just wanna ensure no NPE
                    if (value == -1) {
                        getOption().getValue().getCheck().setGameMode(null);
                    } else {
                        getOption().getValue().getCheck().setGameMode(GameMode.byId(value));
                    }
                }
            }
        };
        if (getOption().getValue().getCheck().getGameMode() == null) {
            gameMode.setValue(-1);
        } else {
            gameMode.setValue(getOption().getValue().getCheck().getGameMode().getId());
        }
        return List.of(blocking, exclusive, ordered, inGame, inGui, server, gameMode);
    }
}
