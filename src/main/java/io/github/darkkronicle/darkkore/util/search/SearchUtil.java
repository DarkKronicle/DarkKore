package io.github.darkkronicle.darkkore.util.search;

import io.github.darkkronicle.darkkore.util.search.finder.Finder;
import lombok.experimental.UtilityClass;
import net.minecraft.text.Text;

import java.util.*;

/**
 * A class used for helping filters find matches and act on them. Helps with Regular Expressions and
 * means that we don't need this in each class.
 */
@UtilityClass
public class SearchUtil {

    /**
     * Method to see if there is a match somewhere with a string with an expression. Is similar to
     * {@link #findMatches(String, String, FindType)} just less expensive since it doesn't need to
     * find every match.
     *
     * @param input String to search.
     * @param toMatch Expression to find.
     * @param type How toMatch should be interpreted.
     * @return If a match is found.
     */
    public boolean isMatch(String input, String toMatch, FindType type) {
        Finder finder = type.getFinder();
        if (finder == null) {
            return false;
        }
        return finder.isMatch(input, toMatch);
    }

    /**
     * Method to see if there is a match somewhere with a string with an expression. Is similar to
     * {@link #findMatches(String, String, FindType)} just less expensive since it doesn't need to
     * find every match.
     *
     * @param input String to search.
     * @param toMatch Expression to find.
     * @param type How toMatch should be interpreted.
     * @return If a match is found.
     */
    public boolean isMatch(Text input, String toMatch, FindType type) {
        Finder finder = type.getFinder();
        if (finder == null) {
            return false;
        }
        return finder.isMatch(input, toMatch);
    }

    /**
     * Get's replacements for a string and matches following the format $\<number\>
     *
     * @param groups Matches that are found, will replace
     * @param input Input with group replacements
     * @return String with replaced groups
     */
    public String replaceGroups(List<StringMatch> groups, String input) {
        // Checks to make it so we don't always have to regex
        if (input.length() < 2 || !input.contains("$")) {
            return input;
        }
        Optional<List<StringMatch>> replace = findMatches(input, "\\$[0-9]", FindType.REGEX);
        if (replace.isEmpty()) {
            return input;
        }
        // Ensure sort
        TreeSet<StringMatch> replaceMatches = new TreeSet<>(replace.get());
        int last = 0;
        StringBuilder edited = new StringBuilder();
        for (StringMatch m : replaceMatches) {
            int digit = Integer.parseInt(m.match.substring(1, 2));
            if (digit == 0 || digit > groups.size()) {
                continue;
            }
            edited.append(input, last, m.start).append(groups.get(digit));
            last = m.end;
        }
        if (last != input.length()) {
            edited.append(input.substring(last));
        }
        return edited.toString();
    }

    /**
     * Method to find all matches within a string. Is similar to {@link #isMatch(String, String,
     * FindType)}}. This method just finds every match and returns it.
     *
     * @param input String to search.
     * @param toMatch Expression to find.
     * @param type How toMatch should be interpreted.
     * @return An Optional containing a list of {@link StringMatch}
     */
    public Optional<List<StringMatch>> findMatches(String input, String toMatch, FindType type) {
        Finder finder = type.getFinder();
        if (finder == null) {
            return Optional.empty();
        }
        Set<StringMatch> matches = new TreeSet<>(finder.getMatches(input, toMatch));
        if (matches.size() != 0) {
            return Optional.of(new ArrayList<>(matches));
        }
        return Optional.empty();
    }

    /**
     * Method to find all matches within a text. Is similar to {@link #isMatch(Text, String,
     * FindType)}}. This method just finds every match and returns it.
     *
     * @param input Text to search.
     * @param toMatch Expression to find.
     * @param type How toMatch should be interpreted.
     * @return An Optional containing a list of {@link StringMatch}
     */
    public Optional<List<StringMatch>> findMatches(Text input, String toMatch, FindType type) {
        Finder finder = type.getFinder();
        if (finder == null) {
            return Optional.empty();
        }
        Set<StringMatch> matches = new TreeSet<>(finder.getMatches(input, toMatch));
        if (matches.size() != 0) {
            return Optional.of(new ArrayList<>(matches));
        }
        return Optional.empty();
    }

    /**
     * Gets first match found based off of conditions
     *
     * @param input String to search
     * @param toMatch Search content
     * @param type {@link FindType} way to search
     * @return Optional of a {@link StringMatch} if found
     */
    public Optional<StringMatch> getMatch(String input, String toMatch, FindType type) {
        Finder finder = type.getFinder();
        if (finder == null) {
            return Optional.empty();
        }
        // Use treeset to sort the matches
        Set<StringMatch> matches = new TreeSet<>(finder.getMatches(input, toMatch));
        // Add and sort matches
        if (matches.size() != 0) {
            return Optional.of(matches.toArray(new StringMatch[0])[0]);
        }
        return Optional.empty();
    }

    /**
     * Gets first match found based off of conditions
     *
     * @param input String to search
     * @param toMatch Search content
     * @param type {@link FindType} way to search
     * @return Optional of a {@link StringMatch} if found
     */
    public Optional<StringMatch> getMatch(Text input, String toMatch, FindType type) {
        Finder finder = type.getFinder();
        if (finder == null) {
            return Optional.empty();
        }
        // Use treeset to sort the matches
        Set<StringMatch> matches = new TreeSet<>(finder.getMatches(input, toMatch));
        // Add and sort matches
        if (matches.size() != 0) {
            return Optional.of(matches.toArray(new StringMatch[0])[0]);
        }
        return Optional.empty();
    }

}
