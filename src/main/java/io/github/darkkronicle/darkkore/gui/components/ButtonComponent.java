package io.github.darkkronicle.darkkore.gui.components;

import io.github.darkkronicle.darkkore.util.Color;
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

    public ButtonComponent(Text text, Color background, Color hover, Consumer<ButtonComponent> onClick) {
        super(text);
        this.background = background;
        this.hover = hover;
        this.onClick = onClick;
        setBackgroundColor(background);
    }

    public ButtonComponent(int width, int height, Text text, Color background, Color hover, Consumer<ButtonComponent> onClick) {
        super(width, height, text);
        setBackgroundColor(background);
    }

    @Override
    public boolean mouseClicked(int x, int y, int mouseX, int mouseY) {
        onClick.accept(this);
        return true;
    }

    @Override
    public void onHoveredImpl(int x, int y, int mouseX, int mouseY) {
        setBackgroundColor(hover);
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
