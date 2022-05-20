package io.github.darkkronicle.darkkore.util.search.finder;

import java.util.regex.Pattern;

public class LiteralFinder extends PatternFinder {
    @Override
    public Pattern getPattern(String toMatch) {
        return Pattern.compile(Pattern.quote(toMatch));
    }
}
