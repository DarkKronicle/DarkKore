package io.github.darkkronicle.darkkore.hotkeys;

public interface InputEvent {

    boolean onKey(int key, int scancode, int action, int modifiers);

    boolean onMouse(int button, int action, int mods);

}
