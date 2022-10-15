package io.github.darkkronicle.darkkore.hotkeys;

import io.github.darkkronicle.darkkore.gui.components.impl.ButtonComponent;
import io.github.darkkronicle.darkkore.util.Color;
import io.github.darkkronicle.darkkore.util.FluidText;
import io.github.darkkronicle.darkkore.util.InputUtil;
import io.github.darkkronicle.darkkore.util.text.RawText;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class HotkeyComponent extends ButtonComponent implements InputEvent {

    private final Color realBackground;
    @Getter private List<Integer> keys;
    private boolean started;
    private boolean focused = false;

    public HotkeyComponent(Screen parent, List<Integer> keys, int width, int height, Color background, Color hover) {
        super(parent, width, height, new FluidText(String.join(" + ", keys.stream().map(InputUtil::getKeyName).toList())), background, hover, null);
        realBackground = background;
        // Don't want to modify (just in case)
        this.keys = new ArrayList<>(keys);
    }

    public void setKeys(List<Integer> keys) {
        this.keys = keys;
        unfocus();
    }

    @Override
    public boolean onKey(int key, int scancode, int action, int modifiers) {
        if (key == GLFW.GLFW_KEY_ESCAPE) {
            keys = new ArrayList<>();
            return true;
        }
        if (started) {
            // Make it so if you immediately click out you keep keys
            started = false;
            keys = new ArrayList<>();
        }
        if (!keys.contains(key)) {
            keys.add(key);
        }
        setFocusedText();
        return false;
    }

    @Override
    public boolean onMouse(int button, int action, int mods) {
        if (!isHovered()) {
            return false;
        }
        if (started) {
            // Make it so if you immediately click out you keep keys
            started = false;
            keys = new ArrayList<>();
        }
        if (!keys.contains(button - 10)) {
            keys.add(button - 10);
        }
        setFocusedText();
        return true;
    }

    @Override
    public void onDestroy() {
        unfocus();
    }

    private void setNormalText() {
        setLines(new FluidText(String.join(" + ", keys.stream().map(InputUtil::getKeyName).toList())));
    }

    private void setFocusedText() {
        setLines(RawText.withColor("> " + String.join(" + ", keys.stream().map(InputUtil::getKeyName).toList()) + " <", new Color(207, 113, 175, 255)));
    }

    @Override
    public boolean mouseClickedImpl(int x, int y, int mouseX, int mouseY, int button) {
        if (isDisabled()) {
            return true;
        }
        if (!focused) {
            playInterfaceSound();
            focus();
        }
        return true;
    }

    public void unfocus() {
        focused = false;
        if (this.equals(HotkeyHandler.getInstance().getActiveComponent())) {
            HotkeyHandler.getInstance().setActiveComponent(null);
        }
        setNormalText();
        setBackground(realBackground);
        setBackground(realBackground);
    }

    public void focus() {
        focused = true;
        setBackground(getHover());
        HotkeyHandler.getInstance().setActiveComponent(this);
        started = true;
        setFocusedText();
    }

    @Override
    public void mouseClickedOutsideImpl(int x, int y, int mouseX, int mouseY, int button) {
        unfocus();
    }
}
