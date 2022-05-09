package io.github.darkkronicle.darkkore.util;

public record PositionedRectangle(int x, int y, int width, int height) {

    public boolean intersects(PositionedRectangle other) {
        return intersects(this, other);
    }

    public static boolean intersects(PositionedRectangle one, PositionedRectangle two) {
        int firstX2;
        int secondX1;
        int firstY2;
        int secondY1;
        if (one.x <= two.x) {
            firstX2 = one.x + one.width;
            secondX1 = two.x;
        } else {
            secondX1 = one.x;
            firstX2 = two.x + two.width;
        }
        if (one.y <= two.y) {
            firstY2 = one.y + one.height;
            secondY1 = two.y;
        } else {
            secondY1 = one.y;
            firstY2 = two.y + two.height;
        }
        return secondX1 <= firstX2 && secondY1 <= firstY2;

    }

}
