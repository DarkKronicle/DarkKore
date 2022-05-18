package io.github.darkkronicle.darkkore.util;

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
     * @return String with formatting
     */
    public String translate(String key) {
        return rawTranslate(key).replaceAll("&", "§");
    }

    /**
     * Retrieves the translation for a string key
     * @param key Key that should be within a language file
     * @return The direct string translated
     */
    public String rawTranslate(String key) {
        return I18n.translate(key);
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
