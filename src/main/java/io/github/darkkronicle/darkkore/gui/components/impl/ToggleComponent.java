package io.github.darkkronicle.darkkore.gui.components.impl;

import io.github.darkkronicle.darkkore.util.*;
import io.github.darkkronicle.darkkore.util.search.StringMatch;
import io.github.darkkronicle.darkkore.util.text.StyleFormatter;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.Map;
import java.util.function.Consumer;

/**
 * A type of {@link ButtonComponent} that cycles between on/off.
 * <p>This automatically handles yes/no button but more customized text can be provided with a %s
 */
public class ToggleComponent extends ButtonComponent {

     @Setter private boolean value;

    @Getter @Setter private Text display;

    /**
     * Creates a new toggle button with automatic width/height and display text as yes/no
     * @param parent
     * @param value Current value of the button
     * @param background {@link Color} of the background
     * @param hover {@link Color} for hover
     * @param onClick {@link Consumer} that takes a boolean for on click
     */
    public ToggleComponent(Screen parent, boolean value, Color background, Color hover, Consumer<Boolean> onClick) {
        this(parent, value, -1, -1, background, hover, onClick);
    }

    /**
     * Creates a new toggle button with display text as yes/no
     * @param parent
     * @param value Current value of the button
     * @param width Width of the button. If less than 0 it is automatically set.
     * @param height Height of the button. If less than 0 it is automatically set.
     * @param background {@link Color} of the background
     * @param hover {@link Color} for hover
     * @param onClick {@link Consumer} that takes a boolean for on click
     */
    public ToggleComponent(Screen parent, boolean value, int width, int height, Color background, Color hover, Consumer<Boolean> onClick) {
        this(parent, new FluidText("%s"), value, width, height, background, hover, onClick);
    }

    /**
     * Creates a new toggle button with automatic width/height and specified display text.
     *
     * <p>The display text should be a text object with %s somewhere in it. That will be replaced with yes/no
     * @param parent
     * @param display The display value for text. Will replace %s with yes/no
     * @param value Current value of the button
     * @param width Width of the button. If less than 0 it is automatically set.
     * @param height Height of the button. If less than 0 it is automatically set.
     * @param background {@link Color} of the background
     * @param hover {@link Color} for hover
     * @param onClick {@link Consumer} that takes a boolean for on click
     */
    public ToggleComponent(Screen parent, Text display, boolean value, int width, int height, Color background, Color hover, Consumer<Boolean> onClick) {
        super(parent, width, height, new FluidText("Blank"), background, hover, null);
        this.value = value;
        setOnClick(button -> onClick.accept(getValue()));
        this.display = display;
        if (autoUpdateWidth) {
            updateWidth();
        } else {
            setLines(getFullText());
        }
    }

    /**
     * {@inheritDoc}
     * This does not automatically set the width because it would be recursive in that case
     */
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
        // Get the maximum width of both values
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

    /** Get the current value of the button */
    public boolean getValue() {
        // We don't use lombok here because it would then be `isValue()` and I prefer this
        return value;
    }

    /** {@inheritDoc} */
    @Override
    public boolean mouseClickedImpl(int x, int y, int mouseX, int mouseY, int button) {
        // Cycle text
        this.value = !value;
        setLines(getFullText());
        return super.mouseClickedImpl(x, y, mouseX, mouseY, button);
    }

    /**
     * Get the current display of the value. In english is yes/no
     * @return The yes/no
     */
    public String getName() {
        return getName(value);
    }

    /** Get the text that should be displayed */
    public Text getFullText() {
        // Super calls this but we want to dynamically do this
        FluidText text;
        if (display == null) {
            text = new FluidText("Blank");
        } else {
            text = new FluidText(display);
        }
        int index = text.getString().indexOf("%s");
        if (index >= 0) {
            text.replaceStrings(Map.of(new StringMatch("%s", index, index + 2), (current, match) -> new FluidText(getName())));
        }
        return text;
    }

    /**
     * Get the display of a value. In english is yes/no
     * @param value Value to translate
     * @return Translated String
     */
    public static String getName(boolean value) {
        return StringUtil.translate("darkkore.component.toggle." + value);
    }

}
