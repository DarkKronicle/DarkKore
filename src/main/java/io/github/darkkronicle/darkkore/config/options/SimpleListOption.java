package io.github.darkkronicle.darkkore.config.options;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public class SimpleListOption extends ListOption<SimpleListOption.SimpleListEntry> {

    private final ImmutableList<SimpleListEntry> options;

    public SimpleListOption(String key, String displayName, String hoverName, String startingKey, String defaultValue, List<String> values) {
        super(key, displayName, hoverName, null);
        List<SimpleListEntry> entries = new ArrayList<>();
        for (String value : values) {
            entries.add(new SimpleListEntry(value, startingKey));
        }
        options = ImmutableList.copyOf(entries);
        this.defaultValue = options.get(0).fromString(defaultValue);
    }

    public void setValue(String string) {
        setValue(options.get(0).fromString(string));
    }

    public class SimpleListEntry implements OptionListEntry<SimpleListEntry> {

        private final String key;
        private final String startingTranslation;

        public SimpleListEntry(String key, String startingTranslation) {
            this.key = key;
            this.startingTranslation = startingTranslation;
        }

        @Override
        public List<SimpleListEntry> getAll() {
            return options;
        }

        @Override
        public String getSaveKey() {
            return key;
        }

        @Override
        public String getDisplayKey() {
            return startingTranslation + "." + key;
        }

        @Override
        public String getInfoKey() {
            return startingTranslation + ".info." + key;
        }
    }

}
