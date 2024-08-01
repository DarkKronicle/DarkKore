package io.github.imide.darkkore_reborn.util;

import io.github.imide.darkkore_reborn.util.text.StyleFormatter;
import lombok.experimental.UtilityClass;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;

/**
 * A utility class for modifying strings
 */
@UtilityClass
public class StringUtil {

    /**
     * Retrieves the translation for a string and converts & to §
     * @param key Key to translate
     * @param arguments Arguments for String.format
     * @return String with formatting
     */
    public String translate(String key, Object... arguments) {
        return rawTranslate(key, arguments).replaceAll("&", "§");
    }

    /**
     * Retrieves the translation for a string key
     * @param key Key that should be within a language file
     * @param arguments Arguments that are used for String.format
     * @return The direct string translated
     */
    public String rawTranslate(String key, Object... arguments) {
        return I18n.translate(key, arguments);
    }

    /**
     * Retrieves the translation for a string key and formats it through {@link StyleFormatter} and converts & to §
     * @param key Key to translate
     * @return A {@link StyleFormatter} text
     */
    public Text translateToText(String key) {
        return StyleFormatter.formatText(new FluidText(translate(key)));
    }

    /**
     * Retrieves the translation for a string key and formats it through {@link StyleFormatter} without converting & to §
     * @param key Key to translate
     * @return A {@link StyleFormatter} text
     */
    public Text rawTranslateToText(String key) {
        return StyleFormatter.formatText(new FluidText(rawTranslate(key)));
    }

}
