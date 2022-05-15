package io.github.darkkronicle.darkkore.config.options;

import java.util.List;

public interface OptionListEntry<T extends OptionListEntry<?>> {

    List<T> getAll();

    default T fromString(String string) {
        for (T entry : getAll()) {
            if (entry.getSaveKey().equals(string)) {
                return entry;
            }
        }
        return null;
    }

    String getSaveKey();

    String getDisplayKey();

    String getInfoKey();

    default T getValue() {
        return (T) this;
    }

    default T next(boolean forward) {
        List<T> all = getAll();
        int index = all.indexOf(getValue());
        if (index < 0) {
            return all.get(0);
        }
        return all.get((index + (forward ? 1 : -1)) % all.size());
    }

}
