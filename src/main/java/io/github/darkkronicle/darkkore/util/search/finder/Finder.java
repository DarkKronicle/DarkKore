package io.github.darkkronicle.darkkore.util.search.finder;

import io.github.darkkronicle.darkkore.util.search.StringMatch;
import net.minecraft.text.Text;

import java.util.List;

public interface Finder {

    boolean isMatch(String input, String toMatch);

    default boolean isMatch(Text input, String toMatch) {
        return isMatch(input.getString(), toMatch);
    }

    List<StringMatch> getMatches(String input, String toMatch);

    default List<StringMatch> getMatches(Text input, String toMatch) {
        return getMatches(input.getString(), toMatch);
    }
}
