package io.github.darkkronicle.darkkore.gui;

import io.github.darkkronicle.darkkore.DarkKore;
import io.github.darkkronicle.darkkore.config.options.Option;
import io.github.darkkronicle.darkkore.config.options.OptionSection;
import io.github.darkkronicle.darkkore.gui.components.impl.ButtonComponent;
import io.github.darkkronicle.darkkore.gui.components.impl.TextComponent;
import io.github.darkkronicle.darkkore.gui.components.transform.ListComponent;
import io.github.darkkronicle.darkkore.gui.components.transform.PositionedComponent;
import io.github.darkkronicle.darkkore.gui.components.transform.ScrollComponent;
import io.github.darkkronicle.darkkore.gui.config.OptionComponent;
import io.github.darkkronicle.darkkore.hotkeys.HotkeyHandler;
import io.github.darkkronicle.darkkore.util.Color;
import io.github.darkkronicle.darkkore.util.Dimensions;
import io.github.darkkronicle.darkkore.util.FluidText;
import io.github.darkkronicle.darkkore.util.StringUtil;
import lombok.Getter;
import net.minecraft.util.Identifier;

import java.util.*;

public class ConfigScreen extends ComponentScreen {

    @Getter
    protected List<Tab> tabs = new ArrayList<>();
    protected Tab currentTab = null;
    protected ScrollComponent tabScroll = null;
    protected ListComponent optionsComp;
    protected ListComponent tabComp;
    protected PositionedComponent optionsPosition;

    protected int yDist = 12;

    public ConfigScreen() {

    }

    public ConfigScreen(List<Tab> tabs) {
        this.tabs = tabs;
    }

    public static ConfigScreen of(List<Option<?>> sections) {
        List<Tab> tabs = new ArrayList<>();
        boolean allSections = true;
        for (Option<?> option : sections) {
            if (!(option instanceof OptionSection)) {
                allSections = false;
                break;
            }
        }
        if (allSections) {
            for (Option<?> opt : sections) {
                tabs.add(populate((OptionSection) opt));
            }
            return new ConfigScreen(tabs);
        }
        return new ConfigScreen(List.of(Tab.ofOptions(new Identifier(DarkKore.MOD_ID, "main"), "main", sections)));
    }

    public static ConfigScreen ofSections(List<OptionSection> sections) {
        List<Tab> tabs = new ArrayList<>();
        for (OptionSection opt : sections) {
            tabs.add(populate(opt));
        }
        return new ConfigScreen(tabs);
    }

    public static Tab populate(OptionSection section) {
        boolean allSections = true;
        for (Option<?> option : section.getOptions()) {
            if (!(option instanceof OptionSection)) {
                allSections = false;
                break;
            }
        }
        if (allSections) {
            List<Tab> nest = new ArrayList<>();
            for (Option<?> opt : section.getOptions()) {
                OptionSection sect = (OptionSection) opt;
                nest.add(populate(sect));
            }
            return Tab.ofTabs(new Identifier(DarkKore.MOD_ID, section.getKey().toLowerCase(Locale.ROOT)), section.getNameKey(), nest);
        }
        return Tab.ofOptions(new Identifier(DarkKore.MOD_ID, section.getKey().toLowerCase(Locale.ROOT)), section.getNameKey(), section.getOptions());
    }

    public static ConfigScreen ofOptions(List<Option<?>> options) {
        List<Tab> tabs = new ArrayList<>();
        tabs.add(Tab.ofOptions(new Identifier(DarkKore.MOD_ID, "main"), "main", options));
        return new ConfigScreen(tabs);
    }

    private void setTab(Tab tab) {
        Dimensions dimensions = Dimensions.getScreen();
        int width = dimensions.getWidth() - 20;
        if (tab == null) {
            tab = tabs.get(0);
        }
        this.currentTab = tab;
        int scroll = tabScroll == null ? 0 : tabScroll.getScrollVal();
        tabComp.clear();
        addTabButtons(0, tabComp.getWidth(), null, tabs, tabComp);
        optionsComp.clear();
        for (Option<?> option : tab.getNestedOptions()) {
            OptionComponent<?, ?> component = OptionComponentHolder.getInstance().convert(this, option, width - 2);
            if (component == null) {
                continue;
            }
            optionsComp.addComponent(component);
        }
        if (tabs.size() > 1) {
            optionsPosition.setY(10 + tabComp.getHeight());
            ((ScrollComponent) optionsPosition.getComponent()).setHeight(Dimensions.getScreen().getHeight() - (10 + tabComp.getHeight()) - 20);
        }
        if (tabScroll != null) {
            tabScroll.setScrollVal(scroll);
        }
    }

    public void addTabButtons(int depth, int width, Tab parent, List<Tab> tabs, ListComponent mainList) {
        ListComponent list = new ListComponent(getParent(), -1 ,-1, false);
        list.setWidth(0);
        if (depth > 0) {
            list.addComponent(new TextComponent(getParent(), new FluidText(">".repeat(depth))));
        }
        Tab selected;
        if (parent == null) {
            if (currentTab == null) {
                currentTab = tabs.get(0);
            }
            selected = currentTab;
        } else {
            selected = parent.getSelected();
        }
        for (Tab tab : tabs) {
            ButtonComponent button = new ButtonComponent(this, StringUtil.translateToText(tab.getDisplayKey()), new Color(100, 100, 100, 100), new Color(150, 150, 150, 150), (comp) -> {
                if (parent != null) {
                    parent.select(tab.getIdentifier());
                    setTab(parent);
                } else {
                    setTab(tab);
                }
            });
            if (tab.equals(selected)) {
                button.setOutlineColor(new Color(255, 255, 255, 255));
                button.setBackground(new Color(50, 50, 50, 150));
            }
            list.addComponent(button);
        }
        ScrollComponent scroll = new ScrollComponent(getParent(), list, width, list.getHeight(), false);
        if (depth == 0) {
            tabScroll = scroll;
        }
        mainList.addComponent(scroll);

        if (selected.getTabs() != null) {
            addTabButtons(depth + 1, width, selected, selected.getTabs(), mainList);
        }
    }

    @Override
    public void initImpl() {
        Dimensions dimensions = Dimensions.getScreen();
        int width = dimensions.getWidth() - 20;
        int y = 10;

        tabComp = new ListComponent(this, width, -1, false);
        optionsComp = new ListComponent(this, width, -1, true);
        optionsPosition = new PositionedComponent(
                this,
                new ScrollComponent(this, optionsComp, width, dimensions.getHeight() - y - 20, true),
                10, y
        );
        setTab(currentTab);
        if (tabs.size() > 1) {
            addComponent(tabComp);
        }
        addComponent(optionsPosition);
    }

    @Override
    public void removed() {
        super.removed();
        HotkeyHandler.getInstance().rebuildHotkeys();
    }
}
