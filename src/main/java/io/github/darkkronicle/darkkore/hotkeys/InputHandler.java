package io.github.darkkronicle.darkkore.hotkeys;

import java.util.ArrayList;
import java.util.List;

public class InputHandler implements InputEvent {

    private final static InputHandler INSTANCE = new InputHandler();

    public static InputHandler getInstance() {
        return INSTANCE;
    }

    private final List<InputEvent> hooks = new ArrayList<>();

    private InputHandler() {}

    public void addHook(InputEvent event) {
        hooks.add(event);
    }

    @Override
    public boolean onKey(int key, int scancode, int action, int modifiers) {
        for (InputEvent event : hooks) {
            if (event.onKey(key, scancode, action, modifiers)) {
                return true;
            }
        }
        return false;
    }
}
