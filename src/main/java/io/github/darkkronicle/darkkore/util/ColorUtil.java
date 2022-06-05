package io.github.darkkronicle.darkkore.util;

import lombok.experimental.UtilityClass;
import net.minecraft.util.Formatting;

/**
 * A static utility class that helps when dealing with colors that use bit shifting, like Minecraft.
 * In here there is a color storage class that makes getting int and creating new colors simple.
 */
@UtilityClass
public class ColorUtil {

    // Probably my best hope for color...
    // https://github.com/parzivail/StarWarsMod/blob/master/src/main/java/com/parzivail/util/ui/GLPalette.java
    // intToColor and colorToInt from parzivail https://github.com/parzivail (slightly modified to
    // account for Alpha)

    /**
     * Turns a packed RGB color into a Color
     *
     * @param rgb The color to unpack
     * @return The new Color
     */
    public Color intToColor4f(int rgb) {
        int alpha = rgb >> 24 & 0xFF;
        int red = rgb >> 16 & 0xFF;
        int green = rgb >> 8 & 0xFF;
        int blue = rgb & 0xFF;
        return new Color(red, green, blue, alpha);
    }

    /**
     * Turns a Color into a packed RGB int
     *
     * @param c The color to pack
     * @return The packed int
     */
    public int colorToInt4f(Color c) {
        int rgb = c.alpha();
        rgb = (rgb << 8) + c.red();
        rgb = (rgb << 8) + c.green();
        rgb = (rgb << 8) + c.blue();
        return rgb;
    }

    public Color fade(Color color, float percent) {
        float alpha = (float) color.alpha();
        return color.withAlpha((int) Math.floor((alpha * percent)));
    }

    /**
     * Grabs the color from a {@link Formatting} formatting.
     * @param formatting The formatting to grab the color from
     * @return The color from formatting as a {@link Color} object, null if there is none
     */
    public Color colorFromFormatting(Formatting formatting) {
        if (!formatting.isColor()) {
            return null;
        }
        return new Color(formatting.getColorValue());
    }

    public static Color getColorFromString(String value) {
        if (value.startsWith("#")) {
            value = value.substring(1);
        }
        StringBuilder valueBuilder = new StringBuilder(value);
        while (valueBuilder.length() < 8) {
            valueBuilder.insert(0, "F");
        }
        value = valueBuilder.toString();

        int alpha = Integer.parseInt(value.substring(0, 2), 16);
        int red = Integer.parseInt(value.substring(2, 4), 16);
        int green = Integer.parseInt(value.substring(4, 6), 16);
        int blue = Integer.parseInt(value.substring(6, 8), 16);

        return new Color(red, green, blue, alpha);

    }
}
