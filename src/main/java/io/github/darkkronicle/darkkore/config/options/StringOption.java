package io.github.darkkronicle.darkkore.config.options;

import io.github.darkkronicle.Konstruct.reader.builder.NodeBuilder;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public class StringOption extends BasicOption<String> {

    @Getter @Setter Predicate<String> check;
    @Getter @Setter private String typeKey;

    public StringOption(String key, String displayName, String hoverName, String defaultValue) {
        this(key, displayName, hoverName, defaultValue, null, null);
    }

    public StringOption(String key, String displayName, String hoverName, String defaultValue, String typeKey, Predicate<String> check) {
        super(key, displayName, hoverName, defaultValue);
        this.check = check;
        this.typeKey = typeKey;
    }

    public static StringOption regexOption(String key, String displayName, String hoverName, String defaultValue) {
        return new StringOption(key, displayName, hoverName, defaultValue, "darkkore.optiontype.info.regex", (string) -> {
            try {
                Pattern.compile(string);
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }

    public static StringOption konstructOption(String key, String displayName, String hoverName, String defaultValue) {
        return new StringOption(key, displayName, hoverName, defaultValue, "darkkore.optiontype.info.konstruct", (string) -> {
            try {
                new NodeBuilder(string).build();
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }

}
