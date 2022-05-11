package io.github.darkkronicle.darkkore;

import io.github.darkkronicle.darkkore.config.ConfigurationManager;
import io.github.darkkronicle.darkkore.config.DarkKoreConfig;
import io.github.darkkronicle.darkkore.intialization.InitializationHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class DarkKore implements ClientModInitializer {

    public final static String MOD_ID = "darkkore";

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        ConfigurationManager.getInstance().add(DarkKoreConfig.getInstance());
        InitializationHandler.getInstance().registerInitializer(MOD_ID, -5, new InitHandler());
    }
}
