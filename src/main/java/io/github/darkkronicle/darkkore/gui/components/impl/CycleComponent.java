package io.github.darkkronicle.darkkore.gui.components.impl;

import io.github.darkkronicle.darkkore.config.options.OptionListEntry;
import io.github.darkkronicle.darkkore.util.Color;
import io.github.darkkronicle.darkkore.util.FluidText;
import io.github.darkkronicle.darkkore.util.StringUtil;
import io.github.darkkronicle.darkkore.util.StyleFormatter;
import lombok.Getter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.util.function.Consumer;

public class CycleComponent<T extends OptionListEntry<T>> extends ButtonComponent {

    @Getter
    private T entry;

    public CycleComponent(T entry, Color background, Color hover, Consumer<T> onClick) {
        this(entry, -1, -1, background, hover, onClick);
    }

    public CycleComponent(T entry, int width, int height, Color background, Color hover, Consumer<T> onClick) {
        super(width, height, StringUtil.translateToText(entry.getDisplayKey()), background, hover, null);
        this.entry = entry;
        setOnClick(button -> onClick.accept(getEntry()));
        if (autoUpdateWidth) {
            updateWidth();
        } else {
            setLines(StringUtil.translateToText(entry.getDisplayKey()));
        }
    }

    public void setEntry(T entry) {
        this.entry = entry;
        setLines(StringUtil.translateToText(entry.getDisplayKey()));
    }

    @Override
    public boolean mouseClickedImpl(int x, int y, int mouseX, int mouseY, int button) {
        setEntry(entry.next(true));
        return super.mouseClickedImpl(x, y, mouseX, mouseY, button);
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
        for (T option : entry.getAll()) {
            setLines(StringUtil.translateToText(option.getDisplayKey()));
            for (Text text : getLines()) {
                maxWidth = Math.max(maxWidth, MinecraftClient.getInstance().textRenderer.getWidth(text) + getLeftPadding() + getRightPadding());
            }
        }
        setEntry(entry);
        setWidth(maxWidth + 4);
    }

}
