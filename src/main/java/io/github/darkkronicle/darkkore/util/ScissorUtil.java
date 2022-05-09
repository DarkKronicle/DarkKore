package io.github.darkkronicle.darkkore.util;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;

public class ScissorUtil {

    private ScissorUtil() {}

    public static void applyScissor(PositionedRectangle rect) {
        GlStateManager._enableScissorTest();
        Window window = MinecraftClient.getInstance().getWindow();
        double factor = window.getScaleFactor();
        GlStateManager._scissorBox((int) (rect.x() * factor), (int) ((window.getScaledHeight() - rect.y() + 1 - rect.height()) * factor), (int) (rect.width() * factor),  (int) ((rect.height() - 3) * factor));
    }

    public static void resetScissor() {
        GlStateManager._disableScissorTest();
    }
}
