package io.github.darkkronicle.darkkore.util.search.finder;

import io.github.darkkronicle.darkkore.util.search.StringMatch;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class PatternFinder implements Finder {

    public abstract Pattern getPattern(String toMatch);

    @Override
    public boolean isMatch(String input, String toMatch) {
        Pattern pattern = getPattern(toMatch);
        return pattern.matcher(input).find();
    }

    @Override
    public List<StringMatch> getMatches(String input, String toMatch) {
        Pattern pattern = getPattern(toMatch);
        Matcher matcher = pattern.matcher(input);
        List<StringMatch> matches = new ArrayList<>();
        while (matcher.find()) {
            matches.add(new StringMatch(matcher.group(), matcher.start(), matcher.end()));
        }
        matcher.reset();
        return matches;
    }
}