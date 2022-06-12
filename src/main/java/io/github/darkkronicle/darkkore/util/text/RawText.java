package io.github.darkkronicle.darkkore.util.text;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.github.darkkronicle.darkkore.util.Color;
import lombok.Getter;
import lombok.Setter;
import lombok.With;
import lombok.experimental.Accessors;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;

/**
 * Class that allows for easy mutable objects that are like minecraft Text.
 *
 * <p>Contains only a {@link String} and {@link Style}
 */
@Accessors(chain = true)
@Environment(EnvType.CLIENT)
public class RawText implements Text {

    /** The message of the {@link RawText} */
    @Getter private TextContent message;

    /** The style of the {@link RawText} */
    @Getter @Setter @With private Style style;

    public RawText withMessage(TextContent content) {
        return new RawText(content, style);
    }

    public RawText withMessage(String content) {
        return new RawText(content, style);
    }

    public RawText(String message, Style style) {
        this.message = new LiteralTextContent(message);
        this.style = style;
    }

    public RawText(TextContent content, Style style) {
        this.message = content;
        this.style = style;
    }

    private RawText(RawText text) {
        this.style = text.withStyle(text.getStyle()).getStyle();
        this.message = text.getMessage();
    }

    public void setMessage(String message) {
        this.message = new LiteralTextContent(message);
    }

    public void setMessage(TextContent content) {
        this.message = content;
    }

    /**
     * Apply a color to the RawText
     *
     * @param string Content
     * @param color Color that will get transfered over
     * @return New RawText
     */
    public static RawText withColor(String string, Color color) {
        if (color == null) {
            return new RawText(string, Style.EMPTY);
        }
        Style style = Style.EMPTY;
        TextColor textColor = TextColor.fromRgb(color.color());
        return RawText.withStyle(string, style.withColor(textColor));
    }

    /**
     * Constructs a RawText based off of {@link Formatting}
     *
     * @param string Content
     * @param formatting {@link Formatting} values
     * @return New RawText
     */
    public static RawText withFormatting(String string, Formatting... formatting) {
        Style style = Style.EMPTY.withFormatting(formatting);
        return new RawText(string, style);
    }

    /**
     * Constructs a new RawText with a string and style.
     *
     * @param string Message
     * @param base {@link Style} of the message
     * @return Constructed RawText
     */
    public static RawText withStyle(String string, Style base) {
        return new RawText(string, base);
    }

    @Override
    public TextContent getContent() {
        return message;
    }

    /**
     * Return's the content
     *
     * @return Content of the RawText
     */
    @Override
    public String getString() {
        return TextUtil.getString(getContent());
    }

    @Deprecated
    @Override
    public List<Text> getSiblings() {
        // This can cause recursion in some situations
        return new ArrayList<>();
    }

    /**
     * Deep copies the {@link RawText}
     *
     * @return Copy of RawText
     */
    public RawText shallowCopy() {
        return new RawText(message, style);
    }

    /**
     * Get's the RawText as {@link OrderedText}
     *
     * @return {@link OrderedText} of the RawText
     */
    @Override
    public OrderedText asOrderedText() {
        return OrderedText.styledForwardsVisitedString(getString(), style);
    }

    /**
     * Applies the content and style to a visitor
     *
     * @param styledVisitor {@link net.minecraft.text.StringVisitable.StyledVisitor} to accept style
     *     and string
     * @param style Default {@link Style} to apply blank values to
     * @param <T> Type returned from the visitor
     * @return The value returned from the visitor
     */
    @Override
    public <T> Optional<T> visit(StyledVisitor<T> styledVisitor, Style style) {
        return this.visitSelf(styledVisitor, style);
    }

    /**
     * Applies the content to a visitor
     *
     * @param visitor {@link net.minecraft.text.StringVisitable.Visitor} to accept the string
     * @param <T> Type returned from the visitor
     * @return Value returned from the visitor
     */
    @Override
    public <T> Optional<T> visit(Visitor<T> visitor) {
        return this.visitSelf(visitor);
    }

    /**
     * Applies the content and style to a visitor
     *
     * @param visitor {@link net.minecraft.text.StringVisitable.StyledVisitor} to accept style and
     *     string
     * @param style Default {@link Style} to apply blank values to
     * @param <T> Type returned from the visitor
     * @return The value returned from the visitor
     */
    public <T> Optional<T> visitSelf(StyledVisitor<T> visitor, Style style) {
        return visitor.accept(this.style.withParent(style), getString());
    }

    /**
     * Applies the content to a visitor
     *
     * @param visitor {@link net.minecraft.text.StringVisitable.Visitor} to accept the string
     * @param <T> Type returned from the visitor
     * @return Value returned from the visitor
     */
    public <T> Optional<T> visitSelf(Visitor<T> visitor) {
        return visitor.accept(getString());
    }

    /**
     * Appends the message content to the message.
     *
     * @param text Text message to add
     * @return MutableText that was added on
     */
    public RawText append(Text text) {
        this.setMessage(new LiteralTextContent(getString() + text.getString()));
        return this;
    }
}
