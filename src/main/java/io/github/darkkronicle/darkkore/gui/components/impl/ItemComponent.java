package io.github.darkkronicle.darkkore.gui.components.impl;

import io.github.darkkronicle.darkkore.gui.components.BasicComponent;
import io.github.darkkronicle.darkkore.util.PositionedRectangle;
import io.github.darkkronicle.darkkore.util.Rectangle;
import io.github.darkkronicle.darkkore.util.render.RenderUtil;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ItemComponent extends BasicComponent {

    /** The {@link ItemStack} to render */
    @Nullable
    @Getter @Setter private ItemStack stack;

    /**
     * Creates an item component with an {@link Item} instead of {@link ItemStack}
     * @param item Item to use
     */
    public ItemComponent(Item item) {
        this(new ItemStack(item));
    }

    /**
     * Creates an item component with an {@link ItemStack}
     * @param stack Stack to use
     */
    public ItemComponent(ItemStack stack) {
        this.stack = stack;
    }

    /**
     * Creates an item component without a stack. This just renders the stack portion as blank.
     */
    public ItemComponent() {
        this((ItemStack) null);
    }

    /** {@inheritDoc} */
    @Override
    public void renderComponent(MatrixStack matrices, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY) {
        ItemStack stack = getStack();
        if (stack != null) {
            RenderUtil.drawItem(matrices, stack, x + 1, y + 1, true);
        }
    }

    /** {@inheritDoc} */
    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(18, 18);
    }
}
