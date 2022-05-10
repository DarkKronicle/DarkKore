package io.github.darkkronicle.darkkore.gui.components;

import io.github.darkkronicle.darkkore.util.Color;
import io.github.darkkronicle.darkkore.util.FluidText;
import io.github.darkkronicle.darkkore.util.StringMatch;
import io.github.darkkronicle.darkkore.util.StringUtil;
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
        this(new FluidText("%s"), value, -1, -1, background, hover, onClick);
    }

    public ToggleComponent(Text display, boolean value, int width, int height, Color background, Color hover, Consumer<Boolean> onClick) {
        super(width, height, new FluidText(getName(value)), background, hover, null);
        setOnClick(button -> onClick.accept(getValue()));
        updateWidth();
        this.value = value;
        this.display = display;
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
    public boolean mouseClicked(int x, int y, int mouseX, int mouseY) {
        return super.mouseClicked(x, y, mouseX, mouseY);
    }

    public String getName() {
        return getName(value);
    }

    private Text getFullText() {
        FluidText text = new FluidText(display);
        int index = text.asString().indexOf("%s");
        if (index > 0) {
            text.replaceStrings(Map.of(new StringMatch("%s", index, index + 2), (current, match) -> new FluidText(getName())));
        }
        return text;
    }

    public static String getName(boolean value) {
        return StringUtil.translate("darkkore.component.toggle." + value);
    }

}
