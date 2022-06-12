package io.github.darkkronicle.darkkore.util.text;

import lombok.experimental.UtilityClass;
import net.minecraft.text.TextContent;

import java.util.Optional;

@UtilityClass
public class TextUtil {

    public String getString(TextContent content) {
        StringBuilder builder = new StringBuilder();
        content.visit((asString) -> {
            builder.append(asString);
            return Optional.of(true);
        });
        return builder.toString();
    }

}
