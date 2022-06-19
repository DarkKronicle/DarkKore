package io.github.darkkronicle.darkkore.gui.components.impl;

import io.github.darkkronicle.darkkore.colors.CommonColors;
import io.github.darkkronicle.darkkore.util.Color;
import io.github.darkkronicle.darkkore.util.ColorUtil;
import io.github.darkkronicle.darkkore.util.SoundUtil;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.function.Consumer;

/**
 * An extension of {@link TextComponent} that will trigger an action on click.
 *
 * <p>This adds a bit of extra padding compared to a normal {@link TextComponent}
 */
public class ButtonComponent extends TextComponent {

    /** The {@link Color} to set when this component is hovered */
    @Getter @Setter private Color hover;

    /** The {@link Color} of the background */
    @Getter @Setter private Color background;

    /** Event to trigger on click */
    @Getter @Setter private Consumer<ButtonComponent> onClick;

    /** Is this button able to be clicked */
    @Getter private boolean disabled = false;

    /**
     * Creates a new button with automatic width/height
     * @param parent
     * @param text {@link Text} to display
     * @param background {@link Color} for the background
     * @param hover {@link Color} for hovering
     * @param onClick {@link Consumer} to trigger on click
     */
    public ButtonComponent(Screen parent, Text text, Color background, Color hover, Consumer<ButtonComponent> onClick) {
        super(parent, text);
        this.background = background;
        this.hover = hover;
        this.onClick = onClick;
        setBackgroundColor(background);
        center = true;
    }

    /**
     * Creates a new button with automatic width/height
     * @param parent
     * @param width Width of the button. If less than 0 it automatically is set.
     * @param height Height of the button. If less than 0 it automatically is set.
     * @param text {@link Text} to display
     * @param background {@link Color} for the background
     * @param hover {@link Color} for hovering
     * @param onClick {@link Consumer} to trigger on click
     */
    public ButtonComponent(Screen parent, int width, int height, Text text, Color background, Color hover, Consumer<ButtonComponent> onClick) {
        super(parent, width, height, text);
        setBackgroundColor(background);
        this.background = background;
        this.hover = hover;
        this.onClick = onClick;
        leftPadding = 4;
        rightPadding = 4;
        center = true;
    }

    /** {@inheritDoc} */
    @Override
    public boolean mouseClickedImpl(int x, int y, int mouseX, int mouseY, int button) {
        if (disabled) {
            return true;
        }
        playInterfaceSound();
        if (onClick != null) {
            onClick.accept(this);
        }
        return true;
    }

    /**
     * Plays a click sound
     */
    protected void playInterfaceSound() {
        SoundUtil.playInterfaceSound();
    }

    /** {@inheritDoc} */
    @Override
    public void onHoveredImpl(int x, int y, int mouseX, int mouseY, boolean hovered) {
        if (!disabled) {
            if (hovered) {
                setBackgroundColor(hover);
            }
        }
        if (!hovered) {
            updateBackground();
        }
    }

    /**
     * {@inheritDoc}
     * <p> This one also adds a bit of padding
     */
    @Override
    protected void updateHeight() {
        super.updateHeight();
        setHeight(getHeight() + 4);
    }

    /**
     * {@inheritDoc}
     * <p>This one also adds a bit of padding
     */
    @Override
    protected void updateWidth() {
        super.updateWidth();
        setWidth(getWidth() + 4);
    }

    public void updateBackground() {
        if (disabled) {
            setBackgroundColor(ColorUtil.blend(getBackground(), CommonColors.getButtonDisabled(), .5f).withAlpha(getBackground().alpha()));
        } else {
            setBackgroundColor(getBackground());
        }
    }

    public void setDisabled(boolean value) {
        disabled = value;
        updateBackground();
    }
}
