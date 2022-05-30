package io.github.darkkronicle.darkkore.config.options;

public class DoubleOption extends NumberOption<Double> {

    public DoubleOption(String key, String displayName, String hoverName, double defaultValue) {
        super(key, displayName, hoverName, defaultValue);
    }

    @Override
    public Double convertNumber(Number number) {
        return number.doubleValue();
    }

    public DoubleOption(String key, String displayName, String hoverName, double defaultValue, double min, double max) {
        super(key, displayName, hoverName, defaultValue, min, max);
    }

}
