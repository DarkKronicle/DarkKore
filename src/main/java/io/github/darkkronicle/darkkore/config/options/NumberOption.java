package io.github.darkkronicle.darkkore.config.options;

import com.electronwill.nightconfig.core.Config;

public class NumberOption<N extends Number & Comparable<N>> extends BasicOption<N> {

    private N min = null;
    private N max = null;

    public NumberOption(String key, String displayName, String hoverName, N defaultValue) {
        super(key, displayName, hoverName, defaultValue);
    }

    public NumberOption(String key, String displayName, String hoverName, N defaultValue, N min, N max) {
        super(key, displayName, hoverName, defaultValue);
        this.min = min;
        this.max = max;
    }

    public void correctValue() {
        if (max != null && value.compareTo(max) > 0) {
            value = max;
        }
        if (min != null && value.compareTo(min) < 0) {
            value = min;
        }
    }

    @Override
    public void load(Config config) {
        super.load(config);
        correctValue();
    }

    @Override
    public void save(Config config) {
        correctValue();
        super.save(config);
    }

}