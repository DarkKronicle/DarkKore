package io.github.darkkronicle.darkkore.gui;

import io.github.darkkronicle.darkkore.config.options.Option;
import io.github.darkkronicle.darkkore.config.options.OptionSection;
import io.github.darkkronicle.darkkore.gui.components.impl.ButtonComponent;
import io.github.darkkronicle.darkkore.gui.components.transform.ListComponent;
import io.github.darkkronicle.darkkore.gui.components.transform.PositionedComponent;
import io.github.darkkronicle.darkkore.gui.components.transform.ScrollComponent;
import io.github.darkkronicle.darkkore.gui.config.OptionComponent;
import io.github.darkkronicle.darkkore.hotkeys.HotkeyHandler;
import io.github.darkkronicle.darkkore.util.Color;
import io.github.darkkronicle.darkkore.util.Dimensions;
import io.github.darkkronicle.darkkore.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigScreen extends ComponentScreen {

    private Map<String, List<Option<?>>> options = null;
    private String currentTab = null;
    private ListComponent optionsComp;

    protected int yDist = 12;

    public ConfigScreen() {

    }

    public ConfigScreen(List<Option<?>> options) {
        this.options = new HashMap<>();
        this.options.put("Main", options);
    }

    public ConfigScreen(Map<String, List<Option<?>>> options) {
        this.options = options;
    }

    public static ConfigScreen of(List<OptionSection> sections) {
        Map<String, List<Option<?>>> tabs = new HashMap<>();
        for (OptionSection section : sections) {
            tabs.put(section.getNameKey(), section.getValue());
        }
        return new ConfigScreen(tabs);
    }

    public Map<String, List<Option<?>>> getOptions() {
        return options;
    }

    private void setTab(String tab) {
        Dimensions dimensions = Dimensions.getScreen();
        int width = dimensions.getWidth() - 20;
        if (tab == null) {
            tab = options.keySet().stream().findFirst().get();
        }
        this.currentTab = tab;
        if (optionsComp == null) {
            optionsComp = new ListComponent(this, width, -1, true);
        } else {
            optionsComp.clear();
        }
        for (Option<?> option : options.get(currentTab)) {
            OptionComponent<?, ?> component = OptionComponentHolder.getInstance().convert(this, option, width - 2);
            if (component == null) {
                continue;
            }
            optionsComp.addComponent(component);
        }
    }

    public List<ButtonComponent> getCategoryButtons() {
        List<ButtonComponent> buttonComponents = new ArrayList<>();
        for (String key : options.keySet()) {
            ButtonComponent button = new ButtonComponent(this, StringUtil.translateToText(key), new Color(100, 100, 100, 100), new Color(150, 150, 150, 150), (comp) -> {
                for (ButtonComponent component : buttonComponents) {
                    component.setDisabled(false);
                    component.setOutlineColor(null);
                }
                setTab(key);
                comp.setDisabled(true);
                comp.setOutlineColor(new Color(180, 180, 180, 180));
            });
            buttonComponents.add(button);
        }
        return buttonComponents;
    }

    @Override
    public void initImpl() {
        Dimensions dimensions = Dimensions.getScreen();
        int width = dimensions.getWidth() - 20;

        setTab(currentTab);
        ListComponent buttons = new ListComponent(this, -1, -1, false);
        for (ButtonComponent component : getCategoryButtons()) {
            buttons.addComponent(component);
        }
        if (buttons.getComponents().size() > 1) {
            addComponent(new PositionedComponent(
                    this,
                    new ScrollComponent(this, buttons, width, buttons.getHeight(), false),
                    10, 10
            ));
        }

        addComponent(
                new PositionedComponent(
                        this,
                        new ScrollComponent(this, optionsComp, width, dimensions.getHeight() - 40, true),
                        10, 40
                )
        );
    }

    @Override
    public void removed() {
        super.removed();
        HotkeyHandler.getInstance().rebuildHotkeys();
    }
}
