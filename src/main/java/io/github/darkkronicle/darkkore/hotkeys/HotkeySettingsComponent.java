package io.github.darkkronicle.darkkore.hotkeys;

import io.github.darkkronicle.darkkore.gui.components.Component;
import io.github.darkkronicle.darkkore.gui.config.OptionComponent;
import io.github.darkkronicle.darkkore.util.Color;
import io.github.darkkronicle.darkkore.util.FluidText;
import io.github.darkkronicle.darkkore.util.StringUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class HotkeySettingsComponent extends OptionComponent<HotkeySettings, HotkeySettingsOption> {

    private HotkeyComponent component;

    public HotkeySettingsComponent(Screen parent, HotkeySettingsOption option, int width) {
        super(parent, option, width, 20);
    }

    @Override
    public Text getConfigTypeInfo() {
        return new FluidText("§7§o" + StringUtil.translate("darkkore.optiontype.info.hotkey"));
    }

    @Override
    public void setValue(HotkeySettings newValue) {
        component.setKeys(newValue.getKeys());
        onUpdate();
    }

    @Override
    public Component getMainComponent() {
        component = new HotkeyComponent(
                parent,
                option.getValue().getKeys(),
                150,
                14,
                new Color(100, 100, 100, 150),
                new Color(150, 150, 150, 150)
        );
        return component;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        option.getValue().setKeys(component.getKeys());
    }
}
