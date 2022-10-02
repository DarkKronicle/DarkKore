package io.github.darkkronicle.darkkore.config.options;

import io.github.darkkronicle.darkkore.colors.ColorAlias;
import io.github.darkkronicle.darkkore.colors.ExtendedColor;
import io.github.darkkronicle.darkkore.config.impl.ConfigObject;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Accessors(chain = true)
public class ExtendedColorOption extends BasicOption<ExtendedColor> {

    @Getter
    @Setter
    private boolean showChromaOptions = true;
    // Incase I ever want to add more options

    public boolean anyExtended() {
        // When adding more use ||
        return showChromaOptions;
    }

    public ExtendedColorOption(String key, String displayName, String hoverName, ExtendedColor defaultValue) {
        super(key, displayName, hoverName, defaultValue);
    }

    @Override
    public void save(ConfigObject config) {
        ConfigObject nested = config.createNew();
        String color = getValue().isAlias() ? getValue().getAliasName() : getValue().getString();
        config.set("color", color);
        ExtendedColor.ChromaOptions chromaOptions = getValue().getChroma();
        ConfigObject chroma = nested.createNew();

        chromaOptions.save(chroma);

        nested.set("chroma", chroma);
        config.set(getKey(), nested);
    }

    @Override
    public void load(ConfigObject config) {
        Optional<Object> obj = config.getOptional(getKey());
        if (obj.isEmpty()) {
            return;
        }
        if (obj.get() instanceof String option) {
            ColorAlias alias = ColorOption.parseString(option);
            if (alias.isAlias()) {
                setValue(new ExtendedColor(alias.getAliasName(), getDefaultValue().getChroma()));
            } else {
                setValue(new ExtendedColor(alias.color(), getDefaultValue().getChroma()));
            }
            return;
        }
        ConfigObject nested = (ConfigObject) obj.get();
        ExtendedColor.ChromaOptions options = getDefaultValue().getChroma();
        String option = nested.get("color");
        ColorAlias alias = ColorOption.parseString(option);

        ConfigObject chroma = nested.get("chroma");
        ExtendedColor.ChromaOptions loaded = ExtendedColor.ChromaOptions.load(chroma, options);

        if (alias.isAlias()) {
            setValue(new ExtendedColor(alias.getAliasName(), loaded));
        } else {
            setValue(new ExtendedColor(alias.color(), loaded));
        }
    }

    public OptionSection getChromaSection() {
        OptionSection chroma = new OptionSection("chroma", "darkkore.option.color.chroma", "darkkore.option.color.info.chroma");
        ExtendedColor.ChromaOptions defaultValue = getDefaultValue().getChroma();
        ExtendedColor.ChromaOptions currentValue = getValue().getChroma();

        BooleanOption activeOption = new BooleanOption(
                "active",
                "darkkore.option.color.chroma.active",
                "darkkore.option.color.chroma.info.active",
                defaultValue.isActive()
        ) {
            @Override
            public void setValue(Boolean value) {
                super.setValue(value);
                ExtendedColor col = ExtendedColorOption.this.getValue();
                ExtendedColorOption.this.setValue(col.withChroma(col.getChroma().withActive(value)));
            }
        };

        activeOption.setValue(currentValue.isActive());
        chroma.addOption(activeOption);

        DoubleOption opacityOption =  new DoubleOption(
                "opacity",
                "darkkore.option.color.chroma.opacity",
                "darkkore.option.color.chroma.info.opacity",
                defaultValue.getOpacity(),
                0,
                1
        ) {
            @Override
            public void setValue(Double value) {
                super.setValue(value);
                ExtendedColor col = ExtendedColorOption.this.getValue();
                ExtendedColorOption.this.setValue(col.withChroma(col.getChroma().withOpacity(value.floatValue())));
            }
        };
        opacityOption.setValue((double) currentValue.getOpacity());
        chroma.addOption(opacityOption);

        DoubleOption sizeOption = new DoubleOption(
                "size",
                "darkkore.option.color.chroma.size",
                "darkkore.option.color.chroma.info.size",
                defaultValue.getSize(),
                0,
                1
        ) {
            @Override
            public void setValue(Double value) {
                super.setValue(value);
                ExtendedColor col = ExtendedColorOption.this.getValue();
                ExtendedColorOption.this.setValue(col.withChroma(col.getChroma().withSize(value.floatValue())));
            }
        };
        chroma.addOption(sizeOption);
        sizeOption.setValue((double) currentValue.getSize());

        DoubleOption speedOption = new DoubleOption(
                "speed",
                "darkkore.option.color.chroma.speed",
                "darkkore.option.color.chroma.info.speed",
                defaultValue.getSpeed(),
                0,
                1
        ) {
            @Override
            public void setValue(Double value) {
                super.setValue(value);
                ExtendedColor col = ExtendedColorOption.this.getValue();
                ExtendedColorOption.this.setValue(col.withChroma(col.getChroma().withSpeed(value.floatValue())));
            }
        };

        chroma.addOption(speedOption);

        speedOption.setValue((double) currentValue.getSpeed());

        DoubleOption saturationOption = new DoubleOption(
                "saturation",
                "darkkore.option.color.chroma.saturation",
                "darkkore.option.color.chroma.info.saturation",
                defaultValue.getSaturation(),
                0,
                1
        ) {
            @Override
            public void setValue(Double value) {
                super.setValue(value);
                ExtendedColor col = ExtendedColorOption.this.getValue();
                ExtendedColorOption.this.setValue(col.withChroma(col.getChroma().withSaturation(value.floatValue())));
            }
        };

        saturationOption.setValue((double) currentValue.getSaturation());

        chroma.addOption(saturationOption);
        return chroma;
    }

    public List<OptionSection> getNestedSections() {
        List<OptionSection> sections = new ArrayList<>();
        if (showChromaOptions) {
            sections.add(getChromaSection());
        }
        return sections;
    }


}
