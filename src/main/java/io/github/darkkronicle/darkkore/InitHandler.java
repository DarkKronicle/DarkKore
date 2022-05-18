package io.github.darkkronicle.darkkore;

import io.github.darkkronicle.darkkore.config.options.ListOption;
import io.github.darkkronicle.darkkore.config.options.StringOption;
import io.github.darkkronicle.darkkore.gui.config.ListOptionComponent;
import io.github.darkkronicle.darkkore.gui.config.StringOptionComponent;
import io.github.darkkronicle.darkkore.settings.DarkKoreConfig;
import io.github.darkkronicle.darkkore.config.options.BooleanOption;
import io.github.darkkronicle.darkkore.gui.ConfigScreen;
import io.github.darkkronicle.darkkore.gui.OptionComponentHolder;
import io.github.darkkronicle.darkkore.gui.config.BooleanOptionComponent;
import io.github.darkkronicle.darkkore.intialization.Initializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.Optional;

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
                            MinecraftClient.getInstance().setScreen(new ConfigScreen(DarkKoreConfig.getInstance().getOptions()));
                        }
                    });
            KeyBinding otherbinding =
                    new KeyBinding(
                            "refinedcreativeinventory.key.inventory",
                            InputUtil.Type.KEYSYM,
                            GLFW.GLFW_KEY_G,
                            "category.keys");
            ClientTickEvents.START_CLIENT_TICK.register(
                    s -> {
                        if (otherbinding.wasPressed()) {
                            MinecraftClient.getInstance().setScreen(new InventoryScreen());
                        }
                    });

        }
        OptionComponentHolder.getInstance().addWithOrder(100, (option, width) -> {
            if (option instanceof BooleanOption) {
                return Optional.of(new BooleanOptionComponent((BooleanOption) option, width));
            }
            return Optional.empty();
        });
        OptionComponentHolder.getInstance().addWithOrder(100, (option, width) -> {
            if (option instanceof StringOption) {
                return Optional.of(new StringOptionComponent((StringOption) option, width));
            }
            return Optional.empty();
        });
        OptionComponentHolder.getInstance().addWithOrder(100, (option, width) -> {
            if (option instanceof ListOption<?>) {
                return Optional.of(new ListOptionComponent<>((ListOption<?>) option, width));
            }
            return Optional.empty();
        });
    }

}
