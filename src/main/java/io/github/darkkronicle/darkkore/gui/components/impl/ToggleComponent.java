package io.github.darkkronicle.darkkore.gui.components.impl;

import io.github.darkkronicle.darkkore.util.*;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.util.Map;
import java.util.function.Consumer;

public class ToggleComponent extends ButtonComponent {

    @Setter
    private boolean value;

    @Getter @Setter
    private Text display;

    public ToggleComponent(boolean value, Color background, Color hover, Consumer<Boolean> onClick) {
        this(value, -1, -1, background, hover, onClick);
    }

    public ToggleComponent(boolean value, int width, int height, Color background, Color hover, Consumer<Boolean> onClick) {
        this(new FluidText("%s"), value, width, height, background, hover, onClick);
    }

    public ToggleComponent(Text display, boolean value, int width, int height, Color background, Color hover, Consumer<Boolean> onClick) {
        super(width, height, new FluidText("Blank"), background, hover, null);
        this.value = value;
        setOnClick(button -> onClick.accept(getValue()));
        this.display = display;
        if (autoUpdateWidth) {
            updateWidth();
        } else {
            setLines(getFullText());
        }
    }

    @Override
    public void setLines(Text text) {
        text = StyleFormatter.formatText(new FluidText(text));
        this.lines = StyleFormatter.wrapText(MinecraftClient.getInstance().textRenderer, autoUpdateWidth ? 10000000 : width, text);
        if (isAutoUpdateHeight()) {
            updateHeight();
        }
    }

    @Override
    protected void updateWidth() {
        int maxWidth = 0;
        value = !value;
        setLines(getFullText());
        for (Text text : getLines()) {
            maxWidth = Math.max(maxWidth, MinecraftClient.getInstance().textRenderer.getWidth(text) + getLeftPadding() + getRightPadding());
        }
        value = !value;
        setLines(getFullText());
        for (Text text : getLines()) {
            maxWidth = Math.max(maxWidth, MinecraftClient.getInstance().textRenderer.getWidth(text) + getLeftPadding() + getRightPadding());
        }
        setWidth(maxWidth + 4);
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public boolean mouseClickedImpl(int x, int y, int mouseX, int mouseY, int button) {
        this.value = !value;
        setLines(getFullText());
        return super.mouseClickedImpl(x, y, mouseX, mouseY, button);
    }

    public String getName() {
        return getName(value);
    }

    public Text getFullText() {
        // Super calls this but we want to dynamically do this
        FluidText text;
        if (display == null) {
            text = new FluidText("Blank");
        } else {
            text = new FluidText(display);
        }
        int index = text.asString().indexOf("%s");
        if (index >= 0) {
            text.replaceStrings(Map.of(new StringMatch("%s", index, index + 2), (current, match) -> new FluidText(getName())));
        }
        return text;
    }

    public static String getName(boolean value) {
        return StringUtil.translate("darkkore.component.toggle." + value);
    }

}
