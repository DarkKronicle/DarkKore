package io.github.darkkronicle.darkkore.util.text;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import io.github.darkkronicle.darkkore.util.Color;
import io.github.darkkronicle.darkkore.util.FluidText;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.ChatMessages;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.util.Unit;

/** Class to format text without losing data. It adds all color and colors with §#RRGGBB are converted to RGB color. */
@Environment(EnvType.CLIENT)
public class StyleFormatter {

    /**
     * An interface to take multiple inputs from a string that has the Section Symbol formatting
     * combined with standard {@link Text} formatting.
     */
    public interface FormattingVisitable {
        /**
         * Accepts a character with information about current formatting
         *
         * @param c Current character
         * @param currentIndex The current index of the raw string
         * @param realIndex The current index without formatting symbols
         * @param textStyle The style that the text currently has
         * @param formattingStyle The style that combines text formatting and formatting symbols
         * @return Whether to continue
         */
        boolean accept(
                char c, int currentIndex, int realIndex, Style textStyle, Style formattingStyle);
    }

    private int currentIndex;
    private int realIndex;
    private int skipBy = 0;
    private Style currentStyle;
    private Style lastTextStyle = null;
    private final FormattingVisitable visitor;
    private final int length;

    /** Results of different parts of formatting */
    private enum Result {
        /** Go up a character */
        INCREMENT,

        /** Go to the next {@link StringVisitable} */
        SKIP,

        /** STOP */
        TERMINATE,
    }

    /**
     * Creates a StyleFormatter for a given length and a {@link FormattingVisitable}
     *
     * <p>This class is meant to be updated with a {@link StringVisitable.StyledVisitor}
     *
     * @param visitor {@link FormattingVisitable} to get updated with each visible character
     * @param length Length of the string
     */
    public StyleFormatter(FormattingVisitable visitor, int length) {
        this.visitor = visitor;
        this.currentIndex = 0;
        this.realIndex = 0;
        this.currentStyle = Style.EMPTY;
        this.length = length;
    }

    /** Sends the current character with the current information to the visitor. */
    private boolean sendToVisitor(char c, Style textStyle) {
        return visitor.accept(c, currentIndex, realIndex, textStyle, currentStyle);
    }

    /** Handles how section symbols get processed */
    private Result updateSection(Style textStyle, Character nextChar, String rest) {
        if (nextChar == null) {
            return Result.SKIP;
        }
        if (nextChar == '#') {
            if (rest.length() > 6) {
                String format = rest.substring(1, 7);
                if ( Pattern.compile("^[0-9a-fA-F]{6}").matcher(format).find()) {
                    currentIndex++;
                    return Result.INCREMENT;
                }
                int red = Integer.parseInt(format.substring(0, 2), 16);
                int green = Integer.parseInt(format.substring(2, 4), 16);
                int blue = Integer.parseInt(format.substring(4, 6), 16);
                TextColor color = TextColor.fromRgb(new Color(red, green, blue, 255).color());
                if (currentStyle.equals(Style.EMPTY) || currentStyle.equals(textStyle)) {
                    // If it's empty or different rely on just the current text style
                    // Arbitrary color
                    currentStyle = textStyle.withExclusiveFormatting(Formatting.BLACK);
                } else {
                    // Styles are different so we take what happened before. This allows us to chain
                    // formatting symbols.

                    // Arbitrary color to reset
                    currentStyle = currentStyle.withExclusiveFormatting(Formatting.BLACK);
                }
                currentStyle = currentStyle.withColor(color);
                currentIndex += 7;
                skipBy = 6;
            }
            return Result.INCREMENT;
        }
        Formatting formatting = Formatting.byCode(nextChar);
        if (formatting != null) {
            if (formatting == Formatting.RESET) {
                // If it resets, just go to what the current text is.
                currentStyle = textStyle;
            } else {
                if (currentStyle.equals(Style.EMPTY) || currentStyle.equals(textStyle)) {
                    // If it's empty or different rely on just the current text style
                    currentStyle = textStyle.withExclusiveFormatting(formatting);
                } else {
                    // Styles are different so we take what happened before. This allows us to chain
                    // formatting symbols.
                    currentStyle = currentStyle.withExclusiveFormatting(formatting);
                }
            }
            if (currentStyle.equals(Style.EMPTY)) {
                currentStyle = textStyle;
            }
        }
        currentIndex++;
        return Result.INCREMENT;
    }

    /**
     * Updates current visitable data as well as signifies whether to end.
     *
     * <p>Calling this method will result in each 'visible' character being sent to the {@link
     * FormattingVisitable}
     *
     * @param textStyle Style of the current string
     * @param string The current string
     * @return Value to terminate. Follows {@link StringVisitable.StyledVisitor} return values.
     */
    public Optional<Optional<Unit>> updateStyle(Style textStyle, String string) {
        if (lastTextStyle == null) {
            lastTextStyle = textStyle;
        }
        currentStyle = textStyle;
        int stringLength = string.length();
        for (int i = 0; i < stringLength; i++) {
            char c = string.charAt(i);
            Character nextChar = null;
            if (i + 1 < stringLength) {
                nextChar = string.charAt(i + 1);
            }
            if (c == '§') {
                skipBy = 0;
                switch (updateSection(textStyle, nextChar, string.substring(i + 1))) {
                    case SKIP:
                        return Optional.empty();
                    case TERMINATE:
                        return Optional.of(StringVisitable.TERMINATE_VISIT);
                    case INCREMENT:
                        i++;
                }
                i += skipBy;
            } else if (sendToVisitor(c, textStyle)) {
                realIndex++;
            } else {
                return Optional.of(StringVisitable.TERMINATE_VISIT);
            }
            currentIndex++;
        }
        lastTextStyle = textStyle;
        return Optional.empty();
    }

    /**
     * Formats text that contains styling data as well as formatting symbols
     *
     * <p>This method is used to remove section symbols while maintaining previous formatting as
     * well as new formatting.
     *
     * @param text Text to reformat
     * @return Formatted text
     */
    public static FluidText formatText(FluidText text) {
        FluidText t = new FluidText();
        int length = text.getString().length();
        StyleFormatter formatter =
                new StyleFormatter(
                        (c, index, formattedIndex, style, formattedStyle) -> {
                            t.append(new RawText(String.valueOf(c), formattedStyle), false);
                            return true;
                        },
                        length);
        text.visit(formatter::updateStyle, Style.EMPTY);
        return t;
    }

    /**
     * Wraps text into multiple lines
     *
     * @param textRenderer TextRenderer to handle text
     * @param scaledWidth Maximum width before the line breaks
     * @param text Text to break up
     * @return List of MutableText of the new lines
     */
    public static List<Text> wrapText(TextRenderer textRenderer, int scaledWidth, Text text) {
        ArrayList<Text> lines = new ArrayList<>();
        for (OrderedText breakRenderedChatMessageLine : ChatMessages.breakRenderedChatMessageLines(text, scaledWidth, textRenderer)) {
            lines.add(new FluidText(breakRenderedChatMessageLine));
        }
        return lines;
    }
}
