package io.github.imide.darkkore_reborn.hotkeys;

import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BasicHotkey implements Hotkey {

    private final HotkeySettings settings;
    private final Runnable onPress;
    private final Runnable onRelease;
    @Getter
    private boolean active;

    public BasicHotkey(HotkeySettings settings, Runnable onPress) {
        this(settings, onPress, null);
    }

    public BasicHotkey(HotkeySettings settings, Runnable onPress, @Nullable Runnable onRelease) {
        this.settings = settings;
        this.onPress = onPress;
        this.onRelease = onRelease;
    }

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
