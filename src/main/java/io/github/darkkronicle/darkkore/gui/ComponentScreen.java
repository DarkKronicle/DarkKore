package io.github.darkkronicle.darkkore.gui;

import io.github.darkkronicle.darkkore.gui.components.Component;
import io.github.darkkronicle.darkkore.util.render.RenderUtil;
import lombok.Getter;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link Screen} to handle rendering components and different elements
 */
public abstract class ComponentScreen extends Screen {

    @Getter
    private List<Component> components = new ArrayList<>();

    protected ComponentScreen() {
        super(new LiteralText(""));
    }

    public void addComponent(Component component) {
        components.add(component);
    }

    public void renderComponents(MatrixStack matrices, int mouseX, int mouseY) {
        for (Component component : components) {
            component.render(matrices, null, 0, 0, mouseX, mouseY);
        }
        for (Component component : components) {
            if (component.shouldPostRender()) {
                component.postRender(matrices, null, 0, 0, mouseX, mouseY);
            }
        }
    }

    @Override
    protected void init() {
        components.clear();
        initImpl();
    }

    public abstract void initImpl();

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float partialTicks) {
        RenderUtil.drawRectangle(matrices, 0, 0, this.width, this.height, 0xB0000000);
        renderComponents(matrices, mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean success = false;
        for (Component component : components) {
            if (component.isHovered()) {
                success = component.mouseClicked(0, 0, (int) mouseX, (int) mouseY, button) || success;
            } else {
                component.mouseClickedOutside(0, 0, (int) mouseX, (int) mouseY, button);
            }
        }
        return success;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        for (Component component : components) {
            if (component.isHovered()) {
                return component.mouseScrolled(0, 0, (int) mouseX, (int) mouseY, amount);
            }
        }
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        for (Component component : components) {
            if (component.isSelected()) {
                if (component.keyPressed(keyCode, scanCode, modifiers)) {
                    return true;
                }
            }
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        for (Component component : components) {
            if (component.isSelected()) {
                if (component.charTyped(chr, modifiers)) {
                    return true;
                }
            }
        }
        return super.charTyped(chr, modifiers);
    }
}
