package io.github.darkkronicle.darkkore.profiles;

import io.github.darkkronicle.darkkore.config.impl.ConfigObject;
import io.github.darkkronicle.darkkore.config.options.*;
import net.minecraft.world.GameMode;

import java.util.List;

public class PlayerContextOption extends BasicOption<PlayerContextCheck> {

    public PlayerContextOption(String key, String displayName, String hoverName, PlayerContextCheck defaultValue) {
        super(key, displayName, hoverName, defaultValue);
    }

    public static void save(String key, PlayerContextCheck value, ConfigObject config) {
        ConfigObject nest = config.createNew();
        if (value.getInGame() != null) {
            nest.set("inGame", value.getInGame());
        }
        if (value.getServer() != null) {
            nest.set("server", value.getServer());
        }
        if (value.getGameMode() != null) {
            nest.set("gameMode", value.getGameMode().getName());
        }
        if (value.getInGui() != null) {
            nest.set("inGui", value.getInGui());
        }
        config.set(key, nest);
    }

    @Override
    public void save(ConfigObject config) {
        save(key, getValue(), config);
    }

    @Override
    public void load(ConfigObject config) {
        // Doesn't matter if null since it will go to default
        setValue(load(key, config));
    }

    public static PlayerContextCheck load(String key, ConfigObject config) {
        ConfigObject nest = config.get(key);
        if (nest == null) {
            return null;
        }
        Boolean inGame = null;
        String server = null;
        GameMode mode = null;
        Boolean inGui = null;

        if (nest.contains("inGame")) {
            inGame = nest.get("inGame");
        }
        if (nest.contains("server")) {
            server = nest.get("server");
        }
        if (nest.contains("gameMode")) {
            String gameMode = nest.get("gameMode");
            for (GameMode gm : GameMode.values()) {
                if (gm.getName().equals(gameMode)) {
                    mode = gm;
                }
            }
        }
        if (nest.contains("inGui")) {
            inGui = nest.get("inGui");
        }
        return new PlayerContextCheck(inGame, server, mode, inGui);
    }

    @Override
    public PlayerContextCheck getDefaultValue() {
        return defaultValue.copy();
    }

    public List<Option<?>> getOptions() {
        PlayerContextCheck option = getValue();
        SimpleListOption inGame = new SimpleListOption(
                "inGame",
                "darkkore.option.hotkey.ingame",
                "darkkore.option.hotkey.info.ingame",
                "darkkore.option.hotkey.ingame",
                convertInGame(getDefaultValue().getInGame()),
                List.of("ingame", "outgame", "both")
        ) {
            @Override
            public void setValue(SimpleListEntry value) {
                super.setValue(value);
                Boolean bool = convertInGame(value.getSaveKey());
                option.setInGame(bool);
            }
        };
        inGame.setValue(convertInGame(getValue().getInGame()));
        SimpleListOption inGui = new SimpleListOption(
                "inGame",
                "darkkore.option.hotkey.ingui",
                "darkkore.option.hotkey.info.ingui",
                "darkkore.option.hotkey.ingui",
                convertInGui(getDefaultValue().getInGui()),
                List.of("ingui", "notgui", "both")
        ) {
            @Override
            public void setValue(SimpleListEntry value) {
                super.setValue(value);
                Boolean bool = convertInGui((value.getSaveKey()));
                option.setInGui(bool);
            }
        };
        inGui.setValue(convertInGui(getValue().getInGui()));
        StringOption server = new StringOption(
                "server",
                "darkkore.option.hotkey.server",
                "darkkore.option.hotkey.info.server",
                getDefaultValue().getServer() == null ? "" : getDefaultValue().getServer()
        ) {
            @Override
            public void setValue(String value) {
                super.setValue(value);
                if (value.isEmpty()) {
                    option.setServer(null);
                } else {
                    option.setServer(value);
                }
            }
        };
        if (getValue().getServer() == null) {
            server.setValue("");
        } else {
            server.setValue(getValue().getServer());
        }
        IntegerOption gameMode = new IntegerOption(
                "gameMode",
                "darkkore.option.hotkey.gamemode",
                "darkkore.option.hotkey.info.gamemode",
                getDefaultValue().getGameMode() == null ? -1 : getDefaultValue().getGameMode().getId(),
                -1,
                3
        ) {
            @Override
            public void setValue(Integer value) {
                super.setValue(value);
                if (value != null) {
                    // Just wanna ensure no NPE
                    if (value == -1) {
                        option.setGameMode(null);
                    } else {
                        option.setGameMode(GameMode.byId(value));
                    }
                }
            }
        };
        if (getValue().getGameMode() == null) {
            gameMode.setValue(-1);
        } else {
            gameMode.setValue(getValue().getGameMode().getId());
        }
        return List.of(inGame, inGui, server, gameMode);
    }


    private static Boolean convertInGame(String value) {
        return switch (value) {
            case "outgame" -> false;
            case "both" -> null;
            default -> true;
        };
    }

    private static String convertInGame(Boolean value) {
        if (value == null) {
            return "both";
        }
        if (value) {
            return "ingame";
        }
        return "outgame";
    }

    private static Boolean convertInGui(String value) {
        return switch (value) {
            case "notgui" -> false;
            case "both" -> null;
            default -> true;
        };
    }

    private static String convertInGui(Boolean value) {
        if (value == null) {
            return "both";
        }
        if (value) {
            return "ingui";
        }
        return "notgui";
    }

    public static List<Option<?>> getOptions(PlayerContextCheck defaultValue, PlayerContextCheck currentValue) {
        PlayerContextOption def = new PlayerContextOption("default", "default", "defalut", defaultValue);
        def.setValue(currentValue);
        return def.getOptions();
    }

}
