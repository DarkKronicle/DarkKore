package io.github.darkkronicle.darkkore.config.options;

import lombok.Getter;
import lombok.Setter;

import java.util.function.Predicate;

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

}
