package io.github.darkkronicle.darkkore.gui.components.impl;

import io.github.darkkronicle.darkkore.config.options.OptionListEntry;
import io.github.darkkronicle.darkkore.util.Color;
import io.github.darkkronicle.darkkore.util.FluidText;
import io.github.darkkronicle.darkkore.util.StringUtil;
import io.github.darkkronicle.darkkore.util.text.StyleFormatter;
import lombok.Getter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.function.Consumer;

/**
 * A {@link ButtonComponent} that cycles between an {@link OptionListEntry}s options
 * @param <T> {@link OptionListEntry} to use
 */
public class CycleComponent<T extends OptionListEntry<T>> extends ButtonComponent {

    /** The current selected {@link T} */
    @Getter protected T entry;

    /**
     * Creates a {@link CycleComponent}
     * @param parent
     * @param entry {@link T} current entry
     * @param background {@link Color} for the background
     * @param hover {@link Color} for on hover
     * @param onClick {@link Consumer} for when the current selection changes
     */
    public CycleComponent(Screen parent, T entry, Color background, Color hover, Consumer<T> onClick) {
        this(parent, entry, -1, -1, background, hover, onClick);
    }

    /**
     * Creates a {@link CycleComponent} with a specified width and height. If width/height are less than 0 it will automatically set them
     * @param parent
     * @param entry {@link T} current entry
     * @param width Width
     * @param height Height
     * @param background {@link Color} for the background
     * @param hover {@link Color} for on hover
     * @param onClick {@link Consumer} for when the current selection changes
     */
    public CycleComponent(Screen parent, T entry, int width, int height, Color background, Color hover, Consumer<T> onClick) {
        super(parent, width, height, StringUtil.translateToText(entry.getDisplayKey()), background, hover, null);
        this.entry = entry;
        setOnClick(button -> onClick.accept(getEntry()));
        if (autoUpdateWidth) {
            updateWidth();
        } else {
            setLines(StringUtil.translateToText(entry.getDisplayKey()));
        }
    }

    /**
     * Sets the entry that this button holds
     * @param entry {@link T} new entry value
     */
    public void setEntry(T entry) {
        this.entry = entry;
        setLines(StringUtil.translateToText(entry.getDisplayKey()));
    }

    /** {@inheritDoc} */
    @Override
    public boolean mouseClickedImpl(int x, int y, int mouseX, int mouseY, int button) {
        setEntry(entry.next(true));
        return super.mouseClickedImpl(x, y, mouseX, mouseY, button);
    }

    /** {@inheritDoc} */
    @Override
    public void setLines(Text text) {
        text = StyleFormatter.formatText(new FluidText(text));
        this.lines = StyleFormatter.wrapText(MinecraftClient.getInstance().textRenderer, autoUpdateWidth ? 10000000 : width, text);
        if (isAutoUpdateHeight()) {
            updateHeight();
        }
    }

    /** {@inheritDoc} */
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
