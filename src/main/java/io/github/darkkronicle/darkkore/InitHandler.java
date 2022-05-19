package io.github.darkkronicle.darkkore;

import io.github.darkkronicle.darkkore.config.options.*;
import io.github.darkkronicle.darkkore.gui.config.*;
import io.github.darkkronicle.darkkore.settings.DarkKoreConfig;
import io.github.darkkronicle.darkkore.gui.ConfigScreen;
import io.github.darkkronicle.darkkore.gui.OptionComponentHolder;
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
        OptionComponentHolder.getInstance().addWithOrder(50, (option, width) -> {
            if (option instanceof DoubleOption) {
                return Optional.of(new DoubleOptionComponent((DoubleOption) option, width));
            }
            return Optional.empty();
        });
        OptionComponentHolder.getInstance().addWithOrder(50, (option, width) -> {
            if (option instanceof IntegerOption) {
                return Optional.of(new IntegerOptionComponent((IntegerOption) option, width));
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
