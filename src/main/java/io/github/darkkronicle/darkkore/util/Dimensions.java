package io.github.darkkronicle.darkkore.util;

import lombok.experimental.UtilityClass;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;

public class Dimensions {

    private final int width;
    private final int height;

    public static Dimensions getScreen() {
        Window window = MinecraftClient.getInstance().getWindow();
        return new Dimensions(window.getScaledWidth(), window.getScaledHeight());
    }

    public static Dimensions getUnscaledScreen() {
        Window window = MinecraftClient.getInstance().getWindow();
        return new Dimensions(window.getWidth(), window.getHeight());
    }

    public Dimensions(Rectangle rectangle) {
        this(rectangle.width(), rectangle.height());
    }

    public Dimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth(float percent) {
        return (int) (getWidth() * percent);
    }

    public int getHeight(double percent) {
        return (int) (getHeight() * percent);
    }

    public Position getTopLeft(int xMargin, int yMargin) {
        return new Position(xMargin, yMargin);
    }

    public Position getTopRight(int xMargin, int yMargin) {
        return new Position(getWidth() - xMargin, yMargin);
    }

    public Position getBottomLeft(int xMargin, int yMargin) {
        return new Position(xMargin, getHeight() - yMargin);
    }

    public Position getBottomRight(int xMargin, int yMargin) {
        return new Position(getWidth() - xMargin, getHeight() - yMargin);
    }

    public Position getTopLeft(float xMargin, float yMargin) {
        return new Position(getWidth(xMargin), getHeight(yMargin));
    }

    public Position getTopRight(float xMargin, float yMargin) {
        return new Position(getWidth(1 - xMargin), getHeight(yMargin));
    }

    public Position getBottomLeft(float xMargin, float yMargin) {
        return new Position(getWidth(xMargin), getHeight(1 - yMargin));
    }

    public Position getBottomRight(float xMargin, float yMargin) {
        return new Position(getWidth(1 - xMargin), getHeight(1 - yMargin));
    }

    public int getCenterX() {
        return getWidth(.5f);
    }

    public int getCenterY() {
        return getHeight(.5f);
    }

    public Position getCenter() {
        return new Position(getCenterX(), getCenterY());
    }

}
