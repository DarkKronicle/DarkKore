package io.github.darkkronicle.darkkore.settings;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import io.github.darkkronicle.darkkore.gui.ConfigScreen;

public class ModMenuImpl implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> new ConfigScreen(DarkKoreConfig.getInstance().getOptions());
    }
}
