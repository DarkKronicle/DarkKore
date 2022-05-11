package io.github.darkkronicle.darkkore;

import io.github.darkkronicle.darkkore.config.DarkKoreConfig;
import io.github.darkkronicle.darkkore.intialization.Initializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class InitHandler implements Initializer {

    @Override
    public void init() {
        if (DarkKoreConfig.getInstance().debug.getValue()) {
            KeyBinding keyBinding =
                    new KeyBinding(
                            "refinedcreativeinventory.key.test",
                            InputUtil.Type.KEYSYM,
                            GLFW.GLFW_KEY_U,
                            "category.keys");
            ClientTickEvents.START_CLIENT_TICK.register(
                    s -> {
                        if (keyBinding.wasPressed()) {
                            MinecraftClient.getInstance().setScreen(new TestScreen());
                        }
                    });
        }
    }

}
