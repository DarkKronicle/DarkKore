package io.github.darkkronicle.darkkore.gui.components.impl;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

/**
 * An {@link ItemComponent} that is bound to a {@link Inventory}
 */
public class InventoryItemComponent extends ItemComponent {

    /** Inventory to get item from */
    @Getter @Setter protected Inventory inventory;

    /** Slot in the inventory to get from */
    @Getter @Setter protected int index;

    public InventoryItemComponent(Inventory inventory, int index) {
        super();
        this.inventory = inventory;
        this.index = index;
    }

    /**
     * {@inheritDoc}
     * <p>This makes sure to get it from the inventory
     */
    @Override
    public ItemStack getStack() {
        return inventory.getStack(index);
    }
}
