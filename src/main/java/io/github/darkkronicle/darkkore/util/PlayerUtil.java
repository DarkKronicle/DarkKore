package io.github.darkkronicle.darkkore.util;

import lombok.experimental.UtilityClass;
import net.minecraft.client.MinecraftClient;

@UtilityClass
public class PlayerUtil {

    /**
     * Returns the server address that the client is currently connected to.
     * @return The server address if connected, 'singleplayer' if singleplayer, 'none' if none.
     */
    public static String getServer() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.isInSingleplayer()) {
            return "singleplayer";
        }
        if (client.getCurrentServerEntry() == null) {
            return "none";
        }
        return client.getCurrentServerEntry().address;
    }

}
