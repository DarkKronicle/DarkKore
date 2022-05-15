package io.github.darkkronicle.darkkore.settings;

import io.github.darkkronicle.darkkore.config.options.OptionListEntry;
import lombok.Getter;
import net.minecraft.block.enums.Instrument;

import java.util.List;

public enum SoundType implements OptionListEntry<SoundType> {
    NORMAL("normal", null),
    CHIME("chime", Instrument.CHIME),
    BIT("bit", Instrument.BIT),
    VIBRAPHONE("vibraphone", Instrument.IRON_XYLOPHONE)
    ;

    private final String key;

    @Getter
    private final Instrument instrument;

    SoundType(String key, Instrument instrument) {
        this.key = key;
        this.instrument = instrument;
    }

    @Override
    public List<SoundType> getAll() {
        return List.of(values());
    }

    @Override
    public SoundType fromString(String string) {
        return OptionListEntry.super.fromString(string);
    }

    @Override
    public String getSaveKey() {
        return key;
    }

    @Override
    public String getDisplayKey() {
        return "darkkore.option.sound." + key;
    }

    @Override
    public String getInfoKey() {
        return "darkkore.option.sound.info." + key;
    }

}
