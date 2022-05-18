package io.github.darkkronicle.darkkore.settings;

import io.github.darkkronicle.darkkore.config.options.OptionListEntry;
import lombok.Getter;
import net.minecraft.block.enums.Instrument;

import java.util.List;

/**
 * Sound type used for button UI sounds
 */
public enum SoundType implements OptionListEntry<SoundType> {

    /** Normal sounds (just button clicks) */
    NORMAL("normal", null),

    /** Chime sounds */
    CHIME("chime", Instrument.CHIME),

    /** Bit sounds */
    BIT("bit", Instrument.BIT),

    /** Vibraphone sounds */
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
