package io.github.darkkronicle.darkkore.util.search;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * A class to store data about a match.
 *
 * <p>This class is comparable based on where it starts.
 */
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class StringMatch implements Comparable<StringMatch> {

    /** The content that was matched */
    public String match;

    /** The index of the start of the match */
    public Integer start;

    /** The index of the end of the match */
    public Integer end;

    @Override
    public int compareTo(StringMatch o) {
        return start.compareTo(o.start);
    }
}
