package io.github.imide.darkkore_reborn;

import io.github.imide.darkkore_reborn.colors.Colors;
import io.github.imide.darkkore_reborn.config.ConfigurationManager;
import io.github.imide.darkkore_reborn.hotkeys.HotkeyHandler;
import io.github.imide.darkkore_reborn.hotkeys.InputHandler;
import io.github.imide.darkkore_reborn.settings.DarkKoreConfig;
import io.github.imide.darkkore_reborn.intialization.InitializationHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

@Environment(EnvType.CLIENT)
public class DarkKore_reborn implements ClientModInitializer {

    public final static String MOD_ID = "darkkore-reborn";

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public final static Random RANDOM = new Random();

    @Override
    public void onInitializeClient() {
        ConfigurationManager.getInstance().add(Colors.getInstance());
        ConfigurationManager.getInstance().add(DarkKoreConfig.getInstance());
        InputHandler.getInstance().addHook(HotkeyHandler.getInstance());
        // This should be called first
        InitializationHandler.getInstance().registerInitializer(MOD_ID, -50, new InitHandler());
    }
}
