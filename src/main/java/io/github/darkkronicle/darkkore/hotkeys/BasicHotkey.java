package io.github.darkkronicle.darkkore.hotkeys;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class BasicHotkey implements Hotkey {

    private HotkeySettings settings;
    private Runnable run;

    @Override
    public List<Integer> getKeys() {
        return settings.getKeys();
    }

    @Override
    public HotkeySettings getSettings() {
        return settings;
    }

    @Override
    public void run() {
        run.run();
    }
}
