package io.github.darkkronicle.darkkore.gui.config;

import io.github.darkkronicle.darkkore.config.options.ColorOption;
import io.github.darkkronicle.darkkore.gui.components.BasicComponent;
import io.github.darkkronicle.darkkore.gui.components.Component;
import io.github.darkkronicle.darkkore.gui.components.impl.TextComponent;
import io.github.darkkronicle.darkkore.gui.components.transform.ListComponent;
import io.github.darkkronicle.darkkore.util.*;
import io.github.darkkronicle.darkkore.util.render.RenderUtil;
import io.github.darkkronicle.darkkore.util.search.FindType;
import io.github.darkkronicle.darkkore.util.search.SearchUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class ColorOptionComponent extends TextOptionComponent<Color, ColorOption> {

    public ColorOptionComponent(Screen parent, ColorOption option, int width) {
        super(parent, option, width);
    }

    @Override
    public Text getConfigTypeInfo() {
        return new FluidText("§7§o" + StringUtil.translate("darkkore.optiontype.info.color"));
    }

    @Override
    public boolean isValid(String string) {
        return SearchUtil.isMatch(string, "^(?:#)?(?:[0-9A-F]{2}){3,4}$", FindType.REGEX);
    }

    @Override
    public String getStringValue() {
        return getOption().getValue().getString();
    }

    @Override
    public Component getMainComponent() {
        ListComponent list = new ListComponent(parent, -1, -1, false);
        list.setLeftPad(0);
        list.setRightPad(0);
        list.setTopPad(0);
        list.setBottomPad(0);
        BasicComponent component = new BasicComponent(parent) {
            @Override
            public Rectangle getBoundingBox() {
                return new Rectangle(14, 14);
            }

            @Override
            public void renderComponent(MatrixStack matrices, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY) {
                RenderUtil.drawRectangle(matrices, x, y, 14, 14, getOption().getValue().color());
            }
        };
        list.addComponent(component);
        list.addComponent(super.getMainComponent());
        return list;
    }

    @Override
    public void setValueFromString(String string) {
        getOption().setValue(ColorUtil.getColorFromString(string));
    }
}
