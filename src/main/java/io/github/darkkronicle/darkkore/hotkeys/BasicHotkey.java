package io.github.darkkronicle.darkkore.hotkeys;

import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BasicHotkey implements Hotkey {

    private HotkeySettings settings;
    private Runnable onPress;
    private Runnable onRelease;

    public BasicHotkey(HotkeySettings settings, Runnable onPress) {
        this(settings, onPress, null);
    }

    public BasicHotkey(HotkeySettings settings, Runnable onPress, @Nullable Runnable onRelease) {
        this.settings = settings;
        this.onPress = onPress;
        this.onRelease = onRelease;
    }

    @Getter
    private boolean active;

    @Override
    public List<Integer> getKeys() {
        return settings.getKeys();
    }

    @Override
    public HotkeySettings getSettings() {
        return settings;
    }

    @Override
    public void setActive(boolean value) {
        if (this.active == value) {
            // Nothing new here
            return;
        }
        this.active = value;
        if (value) {
            run();
        } else {
            runStopped();
        }
    }

    protected void run() {
        onPress.run();
    }

    protected void runStopped() {
        if (onRelease != null) {
            onRelease.run();
        }
    }

}
