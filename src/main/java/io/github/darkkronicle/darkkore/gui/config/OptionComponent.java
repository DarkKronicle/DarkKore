package io.github.darkkronicle.darkkore.gui.config;

import io.github.darkkronicle.darkkore.config.options.Option;
import io.github.darkkronicle.darkkore.gui.components.*;
import io.github.darkkronicle.darkkore.util.*;
import lombok.Getter;
import net.minecraft.client.util.math.MatrixStack;

public abstract class OptionComponent<N, T extends Option<N>> extends MultiComponent {

    @Getter
    protected final T option;

    @Getter
    protected ResetButtonComponent reset;

    @Getter
    protected Component hoverComponent = null;

    public OptionComponent(T option, int width, int height) {
        super(width, height);
        this.option = option;
        addComponents(new Dimensions(getBoundingBox()));
        createHover();
    }

    @Override
    public void onHovered(int x, int y, int mouseX, int mouseY) {
        setBackgroundColor(new Color(100, 100, 100, 100));
        super.onHovered(x, y, mouseX, mouseY);
    }

    @Override
    public void onHoveredStopped(int x, int y, int mouseX, int mouseY) {
        setBackgroundColor(null);
        super.onHoveredStopped(x, y, mouseX, mouseY);
    }

    @Override
    public void postRender(MatrixStack matrices, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY) {
        super.postRender(matrices, renderBounds, x, y, mouseX, mouseY);
        if (hoverComponent != null && isHovered()) {
            hoverComponent.render(matrices, renderBounds, x + 1, y + height + 2, mouseX, mouseY);
        }
    }

    protected void createHover() {
        TextComponent text = new TextComponent(width - 2, -1, new FluidText(StringUtil.translate(option.getInfoKey())));
        text.setLeftPadding(4);
        text.setRightPadding(4);
        text.setBackgroundColor(new Color(20, 20, 20, 255));
        text.setOutlineColor(new Color(76, 13, 127, 255));
        text.setZOffset(100);
        hoverComponent = text;
    }

    public void addComponents(Dimensions bounds) {
        TextComponent nameComp = new TextComponent(bounds.getWidth() - 160, -1, new FluidText(StringUtil.translate(option.getNameKey())));
        addComponent(
                new PositionedComponent(
                        nameComp,
                        4,
                        3,
                        nameComp.getBoundingBox().width(),
                        nameComp.getBoundingBox().height()
                )
        );
        setHeight(nameComp.getHeight() + 6);
        Component comp = getMainComponent();
        addComponent(
                new PositionedComponent(
                        comp,
                        bounds.getWidth() - comp.getBoundingBox().width() - 20,
                        3,
                        comp.getBoundingBox().width(),
                        comp.getBoundingBox().height()
                )
        );
        reset =  new ResetButtonComponent(14,
                new Color(100, 100, 100, 150),
                new Color(150, 150, 150, 150),
                button -> {
                    option.setValue(option.getDefaultValue());
                    setValue(option.getValue());
                    onUpdate();
                }
        );
        addComponent(
                new PositionedComponent(
                        reset,
                        bounds.getWidth() - 16,
                        3,
                        14,
                        14
                )
        );
        onUpdate();
    }

    public void onUpdate() {
        if (option.isDefault()) {
            reset.disable();
        } else {
            reset.enable();
        }
    }

    public abstract void setValue(N newValue);

    public abstract Component getMainComponent();

}
