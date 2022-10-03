package io.github.darkkronicle.darkkore.gui.config;

import io.github.darkkronicle.darkkore.colors.ColorAlias;
import io.github.darkkronicle.darkkore.colors.Colors;
import io.github.darkkronicle.darkkore.colors.CommonColors;
import io.github.darkkronicle.darkkore.colors.ExtendedColor;
import io.github.darkkronicle.darkkore.config.options.ColorOption;
import io.github.darkkronicle.darkkore.config.options.ExtendedColorOption;
import io.github.darkkronicle.darkkore.gui.ConfigScreen;
import io.github.darkkronicle.darkkore.gui.components.BasicComponent;
import io.github.darkkronicle.darkkore.gui.components.impl.ButtonComponent;
import io.github.darkkronicle.darkkore.gui.components.transform.ListComponent;
import io.github.darkkronicle.darkkore.util.*;
import io.github.darkkronicle.darkkore.util.render.RenderUtil;
import io.github.darkkronicle.darkkore.util.search.FindType;
import io.github.darkkronicle.darkkore.util.search.SearchUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class ExtendColorOptionComponent extends TextOptionComponent<ExtendedColor, ExtendedColorOption> {


    public ExtendColorOptionComponent(Screen parent, ExtendedColorOption option, int width) {
        super(parent, option, width);
    }

    public BasicComponent getInnerComponent() {
        return new BasicComponent(parent) {
            @Override
            public Rectangle getBoundingBox() {
                return new Rectangle(14, 14);
            }

            @Override
            public void renderComponent(MatrixStack matrices, PositionedRectangle renderBounds, int x, int y, int mouseX, int mouseY) {
                RenderUtil.drawRectangle(
                        matrices, x, y, 14, 14, getOption().getValue()
                );
            }
        };
    }

    @Override
    public Text getConfigTypeInfo() {
        return new FluidText("§7§o" + StringUtil.translate("darkkore.optiontype.info.extendedcolor"));
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

    @Override
    public void setValueFromString(String string) {
        if (string.startsWith("#")) {
            getOption().setValue(new ExtendedColor(ColorUtil.getColorFromString(string), getOption().getValue().getChroma()));
            return;
        }
        getOption().setValue(new ExtendedColor(string, getOption().getValue().getChroma()));
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
        // This **has** to be an extended color option
        ExtendedColorOption option = getOption();
        if (!option.anyExtended()) {
            return list;
        }
        ButtonComponent settings = new SettingsButtonComponent(
                parent,
                14,
                CommonColors.getButtonColor(),
                CommonColors.getButtonHover(),
                button -> {
                    ConfigScreen screen = ConfigScreen.ofSections(option.getNestedSections());
                    screen.setParent(parent);
                    MinecraftClient.getInstance().setScreen(screen);
                }
        );
        list.addComponent(settings);
        return list;
    }
}
