package io.github.darkkronicle.darkkore.gui.config;

import io.github.darkkronicle.darkkore.config.options.Option;
import io.github.darkkronicle.darkkore.gui.components.*;
import io.github.darkkronicle.darkkore.gui.components.impl.TextComponent;
import io.github.darkkronicle.darkkore.gui.components.transform.MultiComponent;
import io.github.darkkronicle.darkkore.gui.components.transform.PositionedComponent;
import io.github.darkkronicle.darkkore.util.*;
import lombok.Getter;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public abstract class OptionComponent<N, T extends Option<N>> extends MultiComponent {

    @Getter
    protected final T option;

    @Getter
    protected ResetButtonComponent reset;

    @Getter
    protected Component hoverComponent = null;

    public OptionComponent(Screen parent, T option, int width, int height) {
        super(parent, width, height);
        this.option = option;
        addComponents(new Dimensions(getBoundingBox()));
        createHover();
    }

    @Override
    public void onHovered(int x, int y, int mouseX, int mouseY, boolean hovered) {
        if (hovered) {
            setBackgroundColor(new Color(100, 100, 100, 100));
        } else {
            setBackgroundColor(null);
        }
        super.onHovered(x, y, mouseX, mouseY, hovered);
    }

    @Override
    public void postRender(MatrixStack matrices, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY) {
        super.postRender(matrices, renderBounds, x, y, mouseX, mouseY);
        if (hoverComponent != null && isHovered()) {
            hoverComponent.render(matrices, renderBounds, x + 1, y + height + 2, mouseX, mouseY);
        }
    }

    protected void createHover() {
        FluidText fluid = new FluidText(StringUtil.translateToText(option.getInfoKey()));
        fluid.append("\n").append(getConfigTypeInfo());
        TextComponent text = new TextComponent(parent, width - 2, -1, fluid);
        text.setLeftPadding(4);
        text.setRightPadding(4);
        text.setBackgroundColor(new Color(20, 20, 20, 255));
        text.setOutlineColor(new Color(76, 13, 127, 255));
        text.setZOffset(100);
        hoverComponent = text;
    }

    public abstract Text getConfigTypeInfo();


    public void addComponents(Dimensions bounds) {
        TextComponent nameComp = new TextComponent(parent, bounds.getWidth() - 160, -1, StringUtil.translateToText(option.getNameKey()));
        addComponent(
                new PositionedComponent(
                        parent, nameComp,
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
                        parent, comp,
                        bounds.getWidth() - comp.getBoundingBox().width() - 20,
                        3,
                        comp.getBoundingBox().width(),
                        comp.getBoundingBox().height()
                )
        );
        reset =  new ResetButtonComponent(parent, 14,
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
                        parent, reset,
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
