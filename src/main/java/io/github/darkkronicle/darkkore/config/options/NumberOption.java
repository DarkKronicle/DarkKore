package io.github.darkkronicle.darkkore.config.options;

import com.electronwill.nightconfig.core.Config;
import io.github.darkkronicle.darkkore.config.impl.ConfigObject;
import lombok.Getter;

public class NumberOption<N extends Number & Comparable<N>> extends BasicOption<N> {

    @Getter private N min = null;
    @Getter private N max = null;

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
    public void load(ConfigObject config) {
        super.load(config);
        correctValue();
    }

    @Override
    public void save(ConfigObject config) {
        correctValue();
        super.save(config);
    }

}
