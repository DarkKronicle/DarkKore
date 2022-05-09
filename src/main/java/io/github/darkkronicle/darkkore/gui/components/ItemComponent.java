package io.github.darkkronicle.darkkore.gui.components;

import io.github.darkkronicle.darkkore.util.PositionedRectangle;
import io.github.darkkronicle.darkkore.util.Rectangle;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.item.ItemRenderer;
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

    private void drawItem(MatrixStack matrices, ItemStack stack, int x, int y) {
        matrices.push();
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        itemRenderer.zOffset = 50;
        itemRenderer.renderInGuiWithOverrides(stack, x, y);
        itemRenderer.zOffset = 0;
        matrices.pop();
    }

    @Override
    public void renderComponent(MatrixStack matrices, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY) {
        drawItem(matrices, stack, x + 1, y + 1);
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(18, 18);
    }
}
