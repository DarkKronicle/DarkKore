package io.github.darkkronicle.darkkore.hotkeys;

import java.util.List;

public interface Hotkey {

    List<Integer> getKeys();

    HotkeySettings getSettings();

    void run();

}
