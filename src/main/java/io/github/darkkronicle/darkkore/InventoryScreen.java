package io.github.darkkronicle.darkkore;

import io.github.darkkronicle.darkkore.gui.ComponentScreen;
import io.github.darkkronicle.darkkore.gui.components.*;
import io.github.darkkronicle.darkkore.gui.components.impl.InventoryItemComponent;
import io.github.darkkronicle.darkkore.gui.components.impl.ItemComponent;
import io.github.darkkronicle.darkkore.gui.components.impl.TextComponent;
import io.github.darkkronicle.darkkore.gui.components.transform.ListComponent;
import io.github.darkkronicle.darkkore.gui.components.transform.PositionedComponent;
import io.github.darkkronicle.darkkore.gui.components.transform.ScrollComponent;
import io.github.darkkronicle.darkkore.util.Color;
import io.github.darkkronicle.darkkore.util.Dimensions;
import io.github.darkkronicle.darkkore.util.RenderUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;

public class InventoryScreen extends ComponentScreen {

    private ItemStack selectedStack = null;

    @Override
    public void initImpl() {
        Dimensions screen = Dimensions.getScreen();
        ListComponent indexed = new ListComponent(screen.getWidth() - 50, -1, true);
        indexed.setTopPad(10);
        indexed.setBottomPad(10);
        for (ItemGroup group : ItemGroup.GROUPS) {
            ListComponent items = new ListComponent(screen.getWidth() - 50, -1, false);
            createListFromGroup(items, group);
            if (items.getComponents().size() <= 0) {
                continue;
            }
            indexed.addComponent(new TextComponent(group.getDisplayName()));
            indexed.addComponent(items);
        }

        addComponent(new PositionedComponent(
                new ScrollComponent(indexed, screen.getWidth() - 50, screen.getHeight() - 100, true
        ).setOutlineColor(new Color(0, 0, 0, 255)), 40, 10, screen.getWidth() - 50, screen.getHeight() - 100));
        ListComponent hotbar = new ListComponent(-1, -1, false);
        for (int i = 0; i < 9; i++) {
            final int j = i;
            ItemComponent item = new InventoryItemComponent(client.player.getInventory(), i) {
                @Override
                public boolean mouseClicked(int x, int y, int mouseX, int mouseY, int button) {
                    if (selectedStack != null) {
                        client.player.getInventory().setStack(j, selectedStack);
                        client.interactionManager.clickCreativeStack(selectedStack, 36 + j);
                        selectedStack = null;
                    }
                    return true;
                }
            };
            item.setOnHoveredConsumer((comp) -> comp.setBackgroundColor(new Color(150, 100, 100, 150)));
            item.setOnHoveredStoppedConsumer((comp) -> comp.setBackgroundColor(null));
            hotbar.addComponent(item);
        }
        hotbar.setOutlineColor(new Color(0, 0, 0, 255));
        addComponent(new PositionedComponent(
           hotbar, 40, screen.getHeight() - 82, hotbar.getBoundingBox().width(), hotbar.getBoundingBox().height()
        ));
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (super.mouseClicked(mouseX, mouseY, button)) {
            return true;
        }
        selectedStack = null;
        return false;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float partialTicks) {
        super.render(matrices, mouseX, mouseY, partialTicks);
        if (selectedStack != null) {
            RenderUtil.drawItem(matrices, selectedStack, mouseX - 8, mouseY - 8, true, 50);
        }
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
                    public boolean mouseClicked(int x, int y, int mouseX, int mouseY, int button) {
                        selectedStack = this.getStack();
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
