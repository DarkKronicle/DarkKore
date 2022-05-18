package io.github.darkkronicle.darkkore.util;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * A data object that holds an x/y value and a width/height
 */
@AllArgsConstructor
@EqualsAndHashCode
public class PositionedRectangle {

    public final int x;
    public final int y;

    public final int width;
    public final int height;

    /**
     * Checks to see if this rectangle intersects with another
     * @param other Other rectangle
     * @return If they intersect
     */
    public boolean intersects(PositionedRectangle other) {
        return intersects(this, other);
    }

    /**
     * Checks to see if the rectangle has any width/height
     * @return If rectangle is empty
     */
    public boolean isEmpty() {
        return width <= 0 && height <= 0;
    }

    /**
     * Creates a copy of this object
     * @return The copy
     */
    public PositionedRectangle copy() {
        return new PositionedRectangle(x, y, width, height);
    }

    /**
     * Check to see if two {@link PositionedRectangle} intersect each other at any point
     */
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

    /**
     * Generates a new {@link PositionedRectangle} that overlays the current one
     * @param other Rectangle that should intersect
     * @return The intersection
     */
    // Source from https://github.com/shedaniel/cloth-basic-math/blob/master/src/main/java/me/shedaniel/math/Rectangle.java
    public PositionedRectangle intersection(PositionedRectangle other) {
        int tx1 = this.x;
        int ty1 = this.y;
        int rx1 = other.x;
        int ry1 = other.y;
        long tx2 = tx1;
        tx2 += this.width;
        long ty2 = ty1;
        ty2 += this.height;
        long rx2 = rx1;
        rx2 += other.width;
        long ry2 = ry1;
        ry2 += other.height;
        if (tx1 < rx1) {
            tx1 = rx1;
        }
        if (ty1 < ry1) {
            ty1 = ry1;
        }
        if (tx2 > rx2) {
            tx2 = rx2;
        }
        if (ty2 > ry2) {
            ty2 = ry2;
        }
        tx2 -= tx1;
        ty2 -= ty1;
        // tx2,ty2 will never overflow (they will never be
        // larger than the smallest of the two source w,h)
        // they might underflow, though...
        if (tx2 < Integer.MIN_VALUE)
            tx2 = Integer.MIN_VALUE;
        if (ty2 < Integer.MIN_VALUE)
            ty2 = Integer.MIN_VALUE;
        return new PositionedRectangle(tx1, ty1, (int) tx2, (int) ty2);
    }

}
