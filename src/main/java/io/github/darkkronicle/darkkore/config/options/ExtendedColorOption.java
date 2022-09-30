package io.github.darkkronicle.darkkore.config.options;

import io.github.darkkronicle.darkkore.colors.ColorAlias;
import io.github.darkkronicle.darkkore.config.impl.ConfigObject;
import io.github.darkkronicle.darkkore.intialization.Saveable;
import io.github.darkkronicle.darkkore.util.Color;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

public class ExtendedColorOption extends ColorOption {

    @Data
    @AllArgsConstructor
    public static class ChromaOptions implements Saveable {

        public static String DISPLAY_KEY = "darkkore.optiontype.chroma";
        public static String INFO_KEY = "darkkore.optiontype.info.chroma";

        private boolean active;
        private float opacity;
        private float size;
        private float speed;
        private float saturation;

        public static ChromaOptions getDefault() {
            return new ChromaOptions(false, 1, 0.5f, 0.5f, 1);
        }

        public ChromaOptions copy() {
            return new ChromaOptions(active, opacity, size, speed, saturation);
        }

        @Override
        public void save(ConfigObject object) {
            object.set("active", active);
            object.set("opacity", opacity);
            object.set("size", size);
            object.set("speed", speed);
            object.set("saturation", saturation);
        }

        @Override
        public void load(ConfigObject object) {
            object.getOptional("active").ifPresent((opt) -> active = (boolean) opt);
            object.getOptional("opacity").ifPresent((opt) -> opacity = (float) opt);
            object.getOptional("size").ifPresent((opt) -> size = (float) opt);
            object.getOptional("speed").ifPresent((opt) -> speed = (float) opt);
            object.getOptional("saturation").ifPresent((opt) -> saturation = (float) opt);
        }
    }

    @Setter
    @Getter
    private ChromaOptions chromaOptions;

    @Setter
    @Getter
    private ChromaOptions defaultChromaOptions;

    public ExtendedColorOption(String key, String displayName, String hoverName, Color defaultValue, ChromaOptions chromaOptions) {
        super(key, displayName, hoverName, defaultValue);
        this.chromaOptions = chromaOptions.copy();
        this.defaultChromaOptions = chromaOptions.copy();
    }

    public ExtendedColorOption(String key, String displayName, String hoverName, ColorAlias defaultValue, ChromaOptions chromaOptions) {
        super(key, displayName, hoverName, defaultValue);
        this.chromaOptions = chromaOptions;
    }



    @Override
    public void save(ConfigObject config) {
        ConfigObject nested = config.createNew();
        String color = getValue().isAlias() ? getValue().getAliasName() : getValue().getString();
        config.set("color", color);
        ChromaOptions chromaOptions = getChromaOptions();
        ConfigObject chroma = nested.createNew();

        chromaOptions.save(chroma);

        nested.set("chroma", chroma);
        config.set(getKey(), nested);
    }

    @Override
    public void load(ConfigObject config) {
        Optional<Object> obj = config.get(config.get(getKey()));
        if (obj.isEmpty()) {
            return;
        }
        if (obj.get() instanceof String option) {
            setValue(parseString(option));
            return;
        }
        ConfigObject nested = (ConfigObject) obj.get();
        ChromaOptions options = getDefaultChromaOptions().copy();
        String option = nested.get("color");
        setValue(parseString(option));

        ConfigObject chroma = nested.get("chroma");
        options.load(chroma);

        setChromaOptions(options);

        options.load(chroma);

    }

    public List<OptionSection> getNestedSections() {
        OptionSection chroma = new OptionSection("chroma", "darkkore.option.color.chroma", "darkkore.option.color.info.chroma");
        ChromaOptions defaultValue = getDefaultChromaOptions().copy();
        chroma.addOption(
                new BooleanOption(
                        "active",
                        "darkkore.option.color.chroma.active",
                        "darkkore.option.color.chroma.info.active",
                        defaultValue.active
                ) {
                    @Override
                    public void setValue(Boolean value) {
                        super.setValue(value);
                        getChromaOptions().setActive(value);
                    }
                }
        );

        chroma.addOption(
                new DoubleOption(
                        "opacity",
                        "darkkore.option.color.chroma.opacity",
                        "darkkore.option.color.chroma.info.opacity",
                        defaultValue.opacity,
                        0,
                        1
                ) {
                    @Override
                    public void setValue(Double value) {
                        super.setValue(value);
                        getChromaOptions().setOpacity(value.floatValue());
                    }
                }
        );

        chroma.addOption(
                new DoubleOption(
                        "size",
                        "darkkore.option.color.chroma.size",
                        "darkkore.option.color.chroma.info.size",
                        defaultValue.size,
                        0,
                        1
                ) {
                    @Override
                    public void setValue(Double value) {
                        super.setValue(value);
                        getChromaOptions().setSize(value.floatValue());
                    }
                }
        );

        chroma.addOption(
                new DoubleOption(
                        "speed",
                        "darkkore.option.color.chroma.speed",
                        "darkkore.option.color.chroma.info.speed",
                        defaultValue.speed,
                        0,
                        1
                ) {
                    @Override
                    public void setValue(Double value) {
                        super.setValue(value);
                        getChromaOptions().setSpeed(value.floatValue());
                    }
                }
        );

        chroma.addOption(
                new DoubleOption(
                        "saturation",
                        "darkkore.option.color.chroma.saturation",
                        "darkkore.option.color.chroma.info.saturation",
                        defaultValue.saturation,
                        0,
                        1
                ) {
                    @Override
                    public void setValue(Double value) {
                        super.setValue(value);
                        getChromaOptions().setSaturation(value.floatValue());
                    }
                }
        );

        return List.of(chroma);
    }


}
