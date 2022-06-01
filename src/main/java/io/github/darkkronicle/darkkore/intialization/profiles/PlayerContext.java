package io.github.darkkronicle.darkkore.intialization.profiles;

import io.github.darkkronicle.darkkore.util.PlayerUtil;
import lombok.Value;
import net.minecraft.client.MinecraftClient;
import net.minecraft.world.GameMode;

@Value
public class PlayerContext {

    boolean inGame;
    String server;
    GameMode gameMode;
    boolean inGui;

    public static PlayerContext get() {
        MinecraftClient client = MinecraftClient.getInstance();
        boolean inGame = client.world != null;
        String server = PlayerUtil.getServer();
        GameMode gameMode = null;
        if (inGame) {
            gameMode = client.interactionManager.getCurrentGameMode();
        }
        boolean inGui = client.currentScreen != null;
        return new PlayerContext(inGame, server, gameMode, inGui);
    }

}
