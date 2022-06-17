package io.github.darkkronicle.darkkore.gui;

import io.github.darkkronicle.darkkore.config.options.Option;
import lombok.Getter;
import net.minecraft.util.Identifier;

import java.util.List;

public class Tab {

    @Getter
    private final List<Option<?>> options;
    @Getter
    private final List<Tab> tabs;

    @Getter
    private final Identifier identifier;

    private Tab selected = null;

    @Getter
    private final String displayKey;

    protected Tab(Identifier identifier, String displayKey, List<Option<?>> options, List<Tab> tabs) {
        this.options = options;
        this.tabs = tabs;
        this.identifier = identifier;
        this.displayKey = displayKey;
    }

    public Tab getSelected() {
        if (selected == null) {
            selected = tabs.get(0);
        }
        return selected;
    }

    public void select(Identifier tab) {
        if (tabs == null) {
            selected = null;
            return;
        }
        for (Tab t : tabs) {
            if (t.getIdentifier().equals(tab)) {
                selected = t;
                return;
            }
        }
        selected = null;
    }

    public List<Option<?>> getNestedOptions() {
        if (tabs == null) {
            return options;
        }
        if (selected == null) {
            selected = tabs.get(0);
        }
        return selected.getNestedOptions();
    }

    public static Tab ofOptions(Identifier identifier, String displayKey, List<Option<?>> options) {
        return new Tab(identifier, displayKey, options, null);
    }

    public static Tab ofTabs(Identifier identifier, String displayKey, List<Tab> tabs) {
        return new Tab(identifier, displayKey, null, tabs);
    }

}
