package io.github.darkkronicle.darkkore.util;

import lombok.experimental.UtilityClass;
import net.minecraft.client.resource.language.I18n;

@UtilityClass
public class StringUtil {

    public String translate(String key) {
        return I18n.translate(key).replaceAll("&", "§");
    }

}
