package io.github.darkkronicle.darkkore.hotkeys;

import io.github.darkkronicle.darkkore.profiles.PlayerContextCheck;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class HotkeySettings {

    private boolean exclusive;
    private boolean ordered;
    private boolean blocking;

    private List<Integer> keys;

    private PlayerContextCheck check;

}
