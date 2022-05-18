package io.github.darkkronicle.darkkore.util;

public record Rectangle(int width, int height) {

    public boolean isEmpty() {
        return width <= 0 && height <= 0;
    }

}
