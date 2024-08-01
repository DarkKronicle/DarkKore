package io.github.imide.darkkore_reborn.colors;

import io.github.imide.darkkore_reborn.util.Color;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CommonColors {

    public Color getButtonColor() {
        return Colors.getInstance().getColorOrWhite("button_normal");
    }

    public Color getButtonHover() {
        return Colors.getInstance().getColorOrWhite("button_hover");
    }

    public Color getOptionBackgroundHover() {
        return Colors.getInstance().getColorOrWhite("option_background_hover");
    }

    public Color getOptionInvalid() {
        return Colors.getInstance().getColorOrWhite("option_invalid");
    }

    public Color getButtonDisabled() {
        return Colors.getInstance().getColorOrWhite("button_disabled");
    }

}
