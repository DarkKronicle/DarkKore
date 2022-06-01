package io.github.darkkronicle.darkkore.intialization.profiles;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.minecraft.world.GameMode;

@AllArgsConstructor
@Data
public class PlayerContextCheck {

    private Boolean inGame;
    private String server;
    private GameMode gameMode;
    private Boolean inGui;

    public boolean check(PlayerContext context) {
        if (inGame != null && context.isInGame() != inGame) {
            return false;
        }
        if ((inGame == null || !inGame) && !context.isInGame()) {
            // out of game so it should always work
            return true;
        }
        if (inGui != null && context.isInGui() != inGui) {
            return false;
        }
        if (server != null && !context.getServer().equals(server)) {
            return false;
        }
        if (gameMode != null && context.getGameMode() != gameMode) {
            return false;
        }
        return true;
    }

    public boolean check() {
        return check(PlayerContext.get());
    }

    public PlayerContextCheck copy() {
        return new PlayerContextCheck(inGame, server, gameMode, inGui);
    }

    public static PlayerContextCheck getDefault() {
        return new PlayerContextCheck(true, null, null, false);
    }

}
