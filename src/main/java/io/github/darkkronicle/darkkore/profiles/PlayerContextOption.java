package io.github.darkkronicle.darkkore.profiles;

import io.github.darkkronicle.darkkore.config.impl.ConfigObject;
import io.github.darkkronicle.darkkore.config.options.BasicOption;
import net.minecraft.world.GameMode;

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

}
