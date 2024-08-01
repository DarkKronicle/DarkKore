package io.github.imide.darkkore_reborn.util.search.finder;

import java.util.regex.Pattern;

public class UpperLowerFinder extends PatternFinder {
    @Override
    public Pattern getPattern(String toMatch) {
        return Pattern.compile(Pattern.quote(toMatch), Pattern.CASE_INSENSITIVE);
    }
}
