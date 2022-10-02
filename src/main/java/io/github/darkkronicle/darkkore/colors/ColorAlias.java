package io.github.darkkronicle.darkkore.colors;

import io.github.darkkronicle.darkkore.util.Color;

public class ColorAlias extends Color {

    private final String alias;

    public ColorAlias(String alias) {
        super(0);
        this.alias = alias;
    }

    public ColorAlias(Color color) {
        this(color.rawColor());
    }

    public ColorAlias(int color) {
        super(color);
        this.alias = null;
    }

    public ColorAlias(int red, int green, int blue, int alpha) {
        super(red, green, blue, alpha);
        this.alias = null;
    }

    public ColorAlias(int red, int green, int blue, int alpha, int color) {
        super(red, green, blue, alpha, color);
        this.alias = null;
    }

    @Override
    public int red() {
        if (alias == null) {
            return super.red();
        }
        return getAlias().red();
    }

    @Override
    public int green() {
        if (alias == null) {
            return super.green();
        }
        return getAlias().green();
    }

    @Override
    public int blue() {
        if (alias == null) {
            return super.blue();
        }
        return getAlias().blue();
    }

    @Override
    public int alpha() {
        if (alias == null) {
            return super.alpha();
        }
        return getAlias().alpha();
    }

    public Color getAlias()  {
        return Colors.getInstance().getColorOrWhite(alias);
    }

    @Override
    public int rawColor() {
        if (alias == null) {
            return super.rawColor();
        }
        return getAlias().rawColor();
    }

    @Override
    public Color withAlpha(int alpha) {
        return super.withAlpha(alpha);
    }

    public boolean isAlias() {
        return alias != null;
    }

    @Override
    public String getString() {
        if (alias == null) {
            return super.getString();
        }
        return getAlias().getString();
    }

    public String getAliasName() {
        return alias;
    }
}
