package io.github.darkkronicle.darkkore;

import io.github.darkkronicle.darkkore.util.render.RenderUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class TestScreen extends Screen {
    public TestScreen() {
        super(Text.literal("testing"));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        RenderUtil.drawRing(matrices, width / 2f, height / 2f, 100, 75, 0xAA000000);
    }

}
