package io.github.darkkronicle.darkkore.gui.components.impl;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public class InventoryItemComponent extends ItemComponent {

    @Getter @Setter private Inventory inventory;

    @Getter @Setter private int index;

    public InventoryItemComponent(Inventory inventory, int index) {
        super();
        this.inventory = inventory;
        this.index = index;
    }

    @Override
    public ItemStack getStack() {
        return inventory.getStack(index);
    }
}
