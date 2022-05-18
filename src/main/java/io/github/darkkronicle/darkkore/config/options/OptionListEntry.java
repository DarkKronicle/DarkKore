package io.github.darkkronicle.darkkore.config.options;

import java.util.List;

/**
 *
 * @param <T> The options class. Typically, this should be itself. Look at {@link io.github.darkkronicle.darkkore.settings.SoundType} for an example
 */
public interface OptionListEntry<T extends OptionListEntry<?>> {

    /**
     * Gets all the possible values for the list
     * @return All values
     */
    List<T> getAll();

    /**
     * Gets {@link T} from a key
     * @param string Key. This will be from {@link #getSaveKey}
     * @return Null if not found, {@link T} if found
     */
    default T fromString(String string) {
        for (T entry : getAll()) {
            if (entry.getSaveKey().equals(string)) {
                return entry;
            }
        }
        return null;
    }

    /**
     * Key that should be used in serialization
     * @return Key for saving purposes
     */
    String getSaveKey();

    /**
     * Translation key for display name
     * @return Translation name
     */
    String getDisplayKey();

    /**
     * Translation key for information (hover)
     * @return Information key
     */
    String getInfoKey();

    /**
     * Fetches {@link T} that this object contains. This is used for the default functions within this class. By default it just casts {@link T} to this
     * @return {@link T}
     */
    default T getValue() {
        return (T) this;
    }

    /**
     * Cycles to the next value
     * @param forward If true, cycle forward, if false, cycle backwards
     * @return Next vaue
     */
    default T next(boolean forward) {
        List<T> all = getAll();
        int index = all.indexOf(getValue());
        if (index < 0) {
            return all.get(0);
        }
        return all.get((index + (forward ? 1 : -1)) % all.size());
    }

}
