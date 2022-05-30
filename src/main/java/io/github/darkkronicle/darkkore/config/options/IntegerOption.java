package io.github.darkkronicle.darkkore.config.options;

public class IntegerOption extends NumberOption<Integer> {

    public IntegerOption(String key, String displayName, String hoverName, int defaultValue) {
        super(key, displayName, hoverName, defaultValue);
    }

    public IntegerOption(String key, String displayName, String hoverName, int defaultValue, int min, int max) {
        super(key, displayName, hoverName, defaultValue, min, max);
    }

    @Override
    public Integer convertNumber(Number number) {
        return number.intValue();
    }
}
