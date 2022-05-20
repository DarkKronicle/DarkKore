package io.github.darkkronicle.darkkore.util.search;

import io.github.darkkronicle.darkkore.config.options.OptionListEntry;
import io.github.darkkronicle.darkkore.util.search.finder.Finder;
import io.github.darkkronicle.darkkore.util.search.finder.LiteralFinder;
import io.github.darkkronicle.darkkore.util.search.finder.RegexFinder;
import io.github.darkkronicle.darkkore.util.search.finder.UpperLowerFinder;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.function.Supplier;

@AllArgsConstructor
public enum FindType implements OptionListEntry<FindType> {
    /**
     * An exact match found in the input
     */
    LITERAL("literal", LiteralFinder::new),

    /**
     * A match found in the input that is case-insensitive
     */
    UPPERLOWER("upperlower", UpperLowerFinder::new),

    /**
     * A regex match found in the input
     */
    REGEX("regex", RegexFinder::new),
    ;

    public final String configString;

    private final Supplier<Finder> finder;

    public Finder getFinder() {
        return finder.get();
    }

    @Override
    public List<FindType> getAll() {
        return List.of(values());
    }

    @Override
    public String getSaveKey() {
        return configString;
    }

    @Override
    public String getDisplayKey() {
        return "darkkore.findtype." + configString;
    }

    @Override
    public String getInfoKey() {
        return "darkkore.findtype.info." + configString;
    }
}
