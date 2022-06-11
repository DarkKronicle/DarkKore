package io.github.darkkronicle.darkkore.util.search.finder;

import io.github.darkkronicle.darkkore.DarkKore;
import io.github.darkkronicle.darkkore.util.search.StringMatch;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class PatternFinder implements Finder {

    public abstract Pattern getPattern(String toMatch);

    @Override
    public boolean isMatch(String input, String toMatch) {
        try {
            Pattern pattern = getPattern(toMatch);
            return pattern.matcher(input).find();
        } catch (Exception e) {
            DarkKore.LOGGER.log(Level.ERROR, "Invalid pattern with " + input + "!", e);
        }
        return false;
    }

    @Override
    public List<StringMatch> getMatches(String input, String toMatch) {
        try {
            Pattern pattern = getPattern(toMatch);
            Matcher matcher = pattern.matcher(input);
            List<StringMatch> matches = new ArrayList<>();
            while (matcher.find()) {
                matches.add(new StringMatch(matcher.group(), matcher.start(), matcher.end()));
            }
            matcher.reset();
            return matches;
        } catch (Exception e) {
            DarkKore.LOGGER.log(Level.ERROR, "Invalid pattern with " + input + "!", e);
        }
        return new ArrayList<>();
    }
}