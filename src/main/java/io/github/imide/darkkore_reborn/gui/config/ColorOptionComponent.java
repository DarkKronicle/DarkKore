package io.github.imide.darkkore_reborn.gui.config;

import io.github.imide.darkkore_reborn.colors.ColorAlias;
import io.github.imide.darkkore_reborn.colors.Colors;
import io.github.imide.darkkore_reborn.config.options.ColorOption;
import io.github.imide.darkkore_reborn.gui.components.BasicComponent;
import io.github.imide.darkkore_reborn.gui.components.transform.ListComponent;
import io.github.imide.darkkore_reborn.util.*;
import io.github.imide.darkkore_reborn.util.render.RenderUtil;
import io.github.imide.darkkore_reborn.util.search.FindType;
import io.github.imide.darkkore_reborn.util.search.SearchUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ColorOptionComponent extends TextOptionComponent<ColorAlias, ColorOption> {

    public ColorOptionComponent(Screen parent, ColorOption option, int width) {
        super(parent, option, width);
    }

    @Override
    public Text getConfigTypeInfo() {
        return new FluidText("§7§o" + StringUtil.translate("darkkore-reborn.optiontype.info.color"));
    }

    @Override
    public boolean isValid(String string) {
        if (SearchUtil.isMatch(string, "^(?:#)?(?:[0-9A-F]{2}){3,4}$", FindType.REGEX)) {
            return true;
        }
        return Colors.getInstance().getColor(string).isPresent();
    }

    @Override
    public String getStringValue() {
        if (getOption().getValue().isAlias()) {
            return getOption().getValue().getAliasName();
        }
        return getOption().getValue().getString();
    }

    public BasicComponent getInnerComponent() {
        return new BasicComponent(parent) {
            @Override
            public Rectangle getBoundingBox() {
                return new Rectangle(14, 14);
            }

            @Override
            public void renderComponent(DrawContext context, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY) {
                RenderUtil.drawRectangle(context, x, y, 14, 14, getOption().getValue().color());
            }
        };
    }

    @Override
    public ListComponent getMainComponent() {
        ListComponent list = new ListComponent(parent, -1, -1, false);
        list.setLeftPad(0);
        list.setRightPad(0);
        list.setTopPad(0);
        list.setBottomPad(0);

        list.addComponent(getInnerComponent());
        list.addComponent(super.getMainComponent());
        return list;
    }

    @Override
    public void setValueFromString(String string) {
        if (string.startsWith("#")) {
            getOption().setValue(new ColorAlias(ColorUtil.getColorFromString(string)));
            return;
        }
        getOption().setValue(new ColorAlias(string));
    }
}
