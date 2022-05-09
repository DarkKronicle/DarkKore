package io.github.darkkronicle.darkkore;

import io.github.darkkronicle.darkkore.gui.ComponentScreen;
import io.github.darkkronicle.darkkore.gui.components.*;
import io.github.darkkronicle.darkkore.util.Color;
import io.github.darkkronicle.darkkore.util.Dimensions;
import io.github.darkkronicle.darkkore.util.FluidText;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;

public class TestScreen extends ComponentScreen {

    @Override
    public void initImpl() {
        Dimensions screen = Dimensions.getScreen();
        ListComponent indexed = new ListComponent(screen.getWidth() - 50, -1, true);
        indexed.setTopPad(10);
        indexed.setBottomPad(10);
        indexed.addComponent(new TextComponent(new FluidText("Items!")));
        for (ItemGroup group : ItemGroup.GROUPS) {
            indexed.addComponent(new TextComponent(group.getDisplayName()));
            ListComponent items = new ListComponent(screen.getWidth() - 50, -1, false);
            createListFromGroup(items, group);
            indexed.addComponent(items);
        }

        addComponent(new PositionedComponent(
                new ScrollComponent(indexed, screen.getWidth() - 50, screen.getHeight() - 100, true
        ).setOutlineColor(new Color(0, 0, 0, 255)), 40, 10, screen.getWidth() - 50, screen.getHeight() - 100));
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    private void createListFromGroup(ListComponent list, ItemGroup group) {
        for (Item item : Registry.ITEM) {
            if (item.getGroup() != null && item.getGroup().equals(group)) {
                BasicComponent component = new ItemComponent(item) {
                    @Override
                    public boolean mouseClicked(int x, int y, int mouseX, int mouseY) {
                        client.player.getInventory().setStack(0, new ItemStack(item));
                        client.interactionManager.clickCreativeStack(new ItemStack(item), 36);
                        return true;
                    }
                };
                component.setOnHoveredConsumer(comp -> comp.setBackgroundColor(new Color(150, 150, 150, 150)));
                component.setOnHoveredStoppedConsumer(comp -> comp.setBackgroundColor(null));
                list.addComponent(component);
            }
        }
    }

}
