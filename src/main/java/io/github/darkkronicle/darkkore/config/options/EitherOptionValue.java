package io.github.darkkronicle.darkkore.config.options;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public enum EitherOptionValue implements OptionListEntry<EitherOptionValue> {
    YES(true, "yes"),
    NO(false, "no"),
    EITHER(null, "either")
    ;

    @Getter private final Boolean value;
    private final String key;

    @Override
    public List<EitherOptionValue> getAll() {
        return List.of(values());
    }

    @Override
    public String getSaveKey() {
        return key;
    }

    @Override
    public String getDisplayKey() {
        return "darkkore.optiontype.either." + key;
    }

    @Override
    public String getInfoKey() {
        return "darkkore.optiontype.either.info." + key;
    }
}
