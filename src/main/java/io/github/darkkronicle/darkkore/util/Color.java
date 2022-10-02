package io.github.darkkronicle.darkkore.util;

import com.mojang.blaze3d.systems.RenderSystem;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.With;
import lombok.experimental.Accessors;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Shader;

import java.util.function.Supplier;

/**
 * A color utility class that allows for easy mutability of RGBA colors
 */
@Accessors(fluent = true)
public class Color {

    @Getter
    private final int red;

    @Getter
    private final int green;

    @Getter
    private final int blue;

    @Getter
    @With(AccessLevel.PUBLIC)
    private final int alpha;

    private final int color;

    public Color(int color) {
        this.color = color;
        Color completeColor = ColorUtil.intToColor4f(color);
        this.red = completeColor.red();
        this.green = completeColor.green();
        this.blue = completeColor.blue();
        this.alpha = completeColor.alpha();
    }

    public int color() {
        return rawColor();
    }

    public int rawColor() {
        return color;
    }

    /**
     * Get the red value as a float
     * @return Red value 0-1
     */
    public float floatRed() {
        return ((float) red()) / 255;
    }

    /**
     * Get the green value as a float
     * @return Green value 0-1
     */
    public float floatGreen() {
        return ((float) green()) / 255;
    }

    /**
     * Get the blue value as a float
     * @return Blue value 0-1
     */
    public float floatBlue() {
        return ((float) blue()) / 255;
    }

    /**
     * Get the alpha value as a float
     * @return Alpha value 0-1
     */
    public float floatAlpha() {
        return ((float) alpha()) / 255;
    }

    public Color(int red, int green, int blue, int alpha) {
        if (red > 255) {
            red = 255;
        }
        if (green > 255) {
            green = 255;
        }
        if (blue > 255) {
            blue = 255;
        }
        if (alpha > 255) {
            alpha = 255;
        }
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
        this.color = ColorUtil.colorToInt4f(this);
    }

    /**
     * Generated for use of Lombok's @With. The color attribute will not be used with this
     */
    public Color(int red, int green, int blue, int alpha, int color) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
        this.color = ColorUtil.colorToInt4f(this);
    }

    /**
     * Creates a string that has the hex representation of a color #AARRGGBB
     * @return Hex representation of the color
     */
    public String getString() {
        return String.format("#%08X", color);
    }

    public Supplier<Shader> getShader() {
        return GameRenderer::getPositionColorShader;
    }

    public int preRender() {
        RenderSystem.setShaderColor(red() / 255f, green() / 255f, blue() / 255f, alpha() / 255f);
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(getShader());
        return color();
    }

    public void postRender() {
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

}