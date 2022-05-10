package io.github.darkkronicle.darkkore.gui.components;

import io.github.darkkronicle.darkkore.util.PositionedRectangle;
import io.github.darkkronicle.darkkore.util.Rectangle;
import io.github.darkkronicle.darkkore.util.RenderUtil;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemComponent extends BasicComponent {

    @Getter @Setter private ItemStack stack;

    public ItemComponent(Item item) {
        this(new ItemStack(item));
    }

    public ItemComponent(ItemStack stack) {
        this.stack = stack;
    }

    public ItemComponent() {
        this((ItemStack) null);
    }

    @Override
    public void renderComponent(MatrixStack matrices, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY) {
        ItemStack stack = getStack();
        if (stack != null) {
            RenderUtil.drawItem(matrices, stack, x + 1, y + 1, true);
        }
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(18, 18);
    }
}
