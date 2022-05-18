package io.github.darkkronicle.darkkore.gui.components.impl;

import io.github.darkkronicle.darkkore.util.Color;
import io.github.darkkronicle.darkkore.util.SoundUtil;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.text.Text;

import java.util.function.Consumer;

public class ButtonComponent extends TextComponent {

    @Getter @Setter
    private Color hover;

    @Getter @Setter
    private Color background;

    @Getter @Setter
    private Consumer<ButtonComponent> onClick;

    @Getter @Setter
    private boolean disabled = false;

    public ButtonComponent(Text text, Color background, Color hover, Consumer<ButtonComponent> onClick) {
        super(text);
        this.background = background;
        this.hover = hover;
        this.onClick = onClick;
        setBackgroundColor(background);
        center = true;
    }

    public ButtonComponent(int width, int height, Text text, Color background, Color hover, Consumer<ButtonComponent> onClick) {
        super(width, height, text);
        setBackgroundColor(background);
        this.background = background;
        this.hover = hover;
        this.onClick = onClick;
        bottomPadding = 0;
        leftPadding = 4;
        rightPadding = 4;
        center = true;
    }

    @Override
    public boolean mouseClicked(int x, int y, int mouseX, int mouseY, int button) {
        if (disabled) {
            return true;
        }
        playInterfaceSound();
        onClick.accept(this);
        return true;
    }

    protected void playInterfaceSound() {
        SoundUtil.playInterfaceSound();
    }

    @Override
    public void onHoveredImpl(int x, int y, int mouseX, int mouseY) {
        if (!disabled) {
            setBackgroundColor(hover);
        }
    }

    @Override
    public void onHoveredStoppedImpl(int x, int y, int mouseX, int mouseY) {
        setBackgroundColor(background);
    }

    @Override
    protected void updateHeight() {
        super.updateHeight();
        setHeight(getHeight() + 4);
    }

    @Override
    protected void updateWidth() {
        super.updateWidth();
        setWidth(getWidth() + 4);
    }
}
