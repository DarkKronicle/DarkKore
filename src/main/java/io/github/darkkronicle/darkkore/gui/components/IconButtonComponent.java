package io.github.darkkronicle.darkkore.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.darkkronicle.darkkore.DarkKore;
import io.github.darkkronicle.darkkore.gui.components.ButtonComponent;
import io.github.darkkronicle.darkkore.util.Color;
import io.github.darkkronicle.darkkore.util.FluidText;
import io.github.darkkronicle.darkkore.util.PositionedRectangle;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class IconButtonComponent extends ButtonComponent {

    @Setter @Getter private int iconWidth;

    @Setter @Getter private int iconHeight;

    @Setter @Getter private Identifier icon;

    @Setter @Getter private Color shaderColor = new Color(255, 255, 255, 255);

    public IconButtonComponent(Identifier icon, int iconSize, Color background, Color hover, Consumer<ButtonComponent> onClick) {
        this(icon, iconSize + 4, iconSize + 4, iconSize, iconSize, background, hover, onClick);
    }

    public IconButtonComponent(Identifier icon, int width, int height, int iconWidth, int iconHeight, Color background, Color hover, Consumer<ButtonComponent> onClick) {
        super(width, height, new FluidText(), background, hover, onClick);
        this.icon = icon;
        this.iconWidth = iconWidth;
        this.iconHeight = iconHeight;
    }

    @Override
    public void renderComponent(MatrixStack matrices, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(shaderColor.red() / 255f, shaderColor.green() / 255f, shaderColor.blue() / 255f, shaderColor.alpha() / 255f);
        RenderSystem.setShaderTexture(0, icon);
        DrawableHelper.drawTexture(matrices, x + leftPadding, y + topPadding, width - (leftPadding + rightPadding), height - (topPadding - rightPadding), 0, 0, iconWidth, iconHeight, iconWidth, iconHeight);
        RenderSystem.setShaderColor(1, 1, 1,1);
    }
}
