package io.github.darkkronicle.darkkore.hotkeys;

import io.github.darkkronicle.darkkore.intialization.profiles.PlayerContext;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.*;
import java.util.function.Supplier;

public class HotkeyHandler implements InputEvent {

    private final static HotkeyHandler INSTANCE = new HotkeyHandler();

    public static HotkeyHandler getInstance() {
        return INSTANCE;
    }

    private HotkeyHandler() {}

    @Nullable
    @Getter
    @Setter
    private HotkeyComponent activeComponent = null;

    // String should be mod id or some unique smth
    private final Map<Identifier, Supplier<List<Hotkey>>> hotkeys = new HashMap<>();

    private List<Hotkey> allHotkeys = new ArrayList<>();

    private final List<Integer> keysPressed = new ArrayList<>();

    public boolean contains(String modId, String name) {
        return hotkeys.containsKey(new Identifier(modId, name));
    }

    /**
     * Rebuilds all the hotkeys. Should be safe to call back as much as needed
     */
    public void rebuildHotkeys() {
        allHotkeys = new ArrayList<>();
        for (Supplier<List<Hotkey>> supplier : hotkeys.values()) {
            allHotkeys.addAll(supplier.get());
        }
    }

    public Supplier<List<Hotkey>> get(String modId, String name) {
        return hotkeys.get(new Identifier(modId, name));
    }

    /**
     * Adds a supplier of {@link Hotkey}. These can easily be removed and re-added whenever desired.
     *
     * @param modId          The ID of the mod adding it
     * @param name           The category/name of the hotkeys
     * @param hotkeySupplier A supplier
     */
    public void add(String modId, String name, Supplier<List<Hotkey>> hotkeySupplier) {
        hotkeys.put(new Identifier(modId, name), hotkeySupplier);
        rebuildHotkeys();
    }

    public void remove(String modId, String name) {
        hotkeys.remove(new Identifier(modId, name));
        rebuildHotkeys();
    }

    @Override
    public boolean onKey(int key, int scancode, int action, int modifiers) {
        if (action == GLFW.GLFW_PRESS) {
            if (activeComponent != null) {
                activeComponent.onKey(key, scancode, action, modifiers);
                return true;
            }
            if (onInputPress(key)) {
                return true;
            }
        } else if (action == GLFW.GLFW_RELEASE) {
            onInputRelease(key);
        }
        return false;
    }

    private boolean shouldBeRun(Hotkey hotkey, HotkeySettings settings, PlayerContext context) {
        if (settings.getKeys().isEmpty()) {
            return false;
        }
        if (settings.getCheck() != null && !settings.getCheck().check(context)) {
            return false;
        }
        if (settings.isExclusive() && (hotkey.getKeys().size() != keysPressed.size() && !hotkey.getKeys().containsAll(keysPressed))) {
            return false;
        }
        return (settings.isOrdered() && Collections.indexOfSubList(keysPressed, hotkey.getKeys()) >= 0) || (
                !settings.isOrdered() && keysPressed.containsAll(hotkey.getKeys())
        );
    }

    private void onInputRelease(int key) {
        keysPressed.removeIf(num -> num == key);
        PlayerContext context = PlayerContext.get();
        for (Hotkey hotkey : allHotkeys) {
            if (hotkey.isActive()) {
                HotkeySettings settings = hotkey.getSettings();
                if (!shouldBeRun(hotkey, settings, context)) {
                    hotkey.setActive(false);
                }
            }
        }
    }

    private boolean onInputPress(int key) {
        PlayerContext context = PlayerContext.get();
        keysPressed.add(key);
        for (Hotkey hotkey : allHotkeys) {
            HotkeySettings settings = hotkey.getSettings();
            if (hotkey.isActive()) {
                if (!shouldBeRun(hotkey, settings, context)) {
                    hotkey.setActive(false);
                }
            } else if (shouldBeRun(hotkey, settings, context)) {
                hotkey.setActive(true);
                if (settings.isBlocking()) {
                    return true;
                }
                break;
            }
        }
        return false;
    }

    @Override
    public boolean onMouse(int button, int action, int mods) {
        int serial = button - 10;
        if (action == GLFW.GLFW_PRESS) {
            if (activeComponent != null) {
                return activeComponent.onMouse(button, action, mods);
            }
            onInputPress(serial);
        } else {
            onInputRelease(serial);
        }
        return false;
    }

}
