package io.github.darkkronicle.darkkore.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;

/**
 * A class to help make getting positions with rectangles easier
 */
public class Dimensions {

    private final int width;
    private final int height;

    /**
     * Generates a {@link Dimensions} from Minecraft's {@link Window}
     * @return New object
     */
    public static Dimensions getScreen() {
        Window window = MinecraftClient.getInstance().getWindow();
        return new Dimensions(window.getScaledWidth(), window.getScaledHeight());
    }

    /**
     * Generates a {@link Dimensions} from Minecraft's {@link Window} that is unscaled
     * @return New object
     */
    public static Dimensions getUnscaledScreen() {
        Window window = MinecraftClient.getInstance().getWindow();
        return new Dimensions(window.getWidth(), window.getHeight());
    }

    /**
     * Creates an object with a specified {@link Rectangle}
     * @param rectangle Rectangle to use
     */
    public Dimensions(Rectangle rectangle) {
        this(rectangle.width(), rectangle.height());
    }

    /**
     * Creates an object with a specified width/height
     * @param width Width of rectangle
     * @param height Height of rectangle
     */
    public Dimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Width of the dimensions
     * @return Width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Height of the dimensions
     * @return Height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Get a percentage of the width.
     *
     * <p>.5 is center
     * @param percent Percentage 0-1 of width to get
     * @return The percentage of the width
     */
    public int getWidth(float percent) {
        return (int) (getWidth() * percent);
    }

    /**
     * Get a percentage of the height.
     *
     * <p>.5 is center
     * @param percent Percentage 0-1 of height to get
     * @return The percentage of the height
     */
    public int getHeight(double percent) {
        return (int) (getHeight() * percent);
    }

    /**
     * Gets the top left of the Dimension with an optional padding in both axis
     * @param xMargin x padding from the top left
     * @param yMargin y padding from the top left
     * @return Position with specified coordinates
     */
    public Position getTopLeft(int xMargin, int yMargin) {
        return new Position(xMargin, yMargin);
    }

    /**
     * Gets the top right of the Dimension with an optional padding in both axis
     * @param xMargin x padding from the top right
     * @param yMargin y padding from the top right
     * @return Position with specified coordinates
     */
    public Position getTopRight(int xMargin, int yMargin) {
        return new Position(getWidth() - xMargin, yMargin);
    }

    /**
     * Gets the bottom left of the Dimension with an optional padding in both axis
     * @param xMargin x padding from the bottom left
     * @param yMargin y padding from the bottom left
     * @return Position with specified coordinates
     */
    public Position getBottomLeft(int xMargin, int yMargin) {
        return new Position(xMargin, getHeight() - yMargin);
    }

    /**
     * Gets the bottom right of the Dimension with an optional padding in both axis
     * @param xMargin x padding from the bottom right
     * @param yMargin y padding from the bottom right
     * @return Position with specified coordinates
     */
    public Position getBottomRight(int xMargin, int yMargin) {
        return new Position(getWidth() - xMargin, getHeight() - yMargin);
    }

    /**
     * Gets the top left of the Dimension with an optional padding of the percentage of width/height
     * @param xMargin x padding in percentage of width
     * @param yMargin y padding in percentage of height
     * @return Position with specified coordinates
     */
    public Position getTopLeft(float xMargin, float yMargin) {
        return new Position(getWidth(xMargin), getHeight(yMargin));
    }

    /**
     * Gets the top right of the Dimension with an optional padding of the percentage of width/height
     * @param xMargin x padding in percentage of width
     * @param yMargin y padding in percentage of height
     * @return Position with specified coordinates
     */
    public Position getTopRight(float xMargin, float yMargin) {
        return new Position(getWidth(1 - xMargin), getHeight(yMargin));
    }

    /**
     * Gets the bottom left of the Dimension with an optional padding of the percentage of width/height
     * @param xMargin x padding in percentage of width
     * @param yMargin y padding in percentage of height
     * @return Position with specified coordinates
     */
    public Position getBottomLeft(float xMargin, float yMargin) {
        return new Position(getWidth(xMargin), getHeight(1 - yMargin));
    }

    /**
     * Gets the bottom right of the Dimension with an optional padding of the percentage of width/height
     * @param xMargin x padding in percentage of width
     * @param yMargin y padding in percentage of height
     * @return Position with specified coordinates
     */
    public Position getBottomRight(float xMargin, float yMargin) {
        return new Position(getWidth(1 - xMargin), getHeight(1 - yMargin));
    }

    /**
     * Gets the center in X value
     * @return The center X
     */
    public int getCenterX() {
        return getWidth(.5f);
    }

    /**
     * Gets the center in Y value
     * @return The center Y
     */
    public int getCenterY() {
        return getHeight(.5f);
    }

    /**
     * Gets the center of the rectangle
     * @return {@link Position} of the center
     */
    public Position getCenter() {
        return new Position(getCenterX(), getCenterY());
    }

}
