package io.github.darkkronicle.darkkore.util;

import io.github.darkkronicle.darkkore.DarkKore;
import io.github.darkkronicle.darkkore.settings.DarkKoreConfig;
import lombok.experimental.UtilityClass;
import net.minecraft.block.enums.Instrument;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import org.jetbrains.annotations.Nullable;

@UtilityClass
public class SoundUtil {

    private final String[] NOTES = new String[]{"F#", "G", "G#", "A", "A#", "B", "C", "C#", "D", "D#", "E", "F"};
    private final int[] PENTATONIC = new int[]{0, 2, 4, 7, 9, 12, 14, 16, 19, 21, 24};

    public void playInterfaceSound() {
        playInterfaceSound(DarkKoreConfig.getInstance().soundType.getValue().getInstrument());
    }

    public void playInterfaceSound(@Nullable Instrument instrument) {
        if (instrument == null) {
            playSound(SoundEvents.UI_BUTTON_CLICK, 1, 3f);
        } else {
            playPentatonic(instrument, 3f);
        }
    }

    public void playNote(Instrument instrument, int note, float volume) {
        float pitch = (float)Math.pow(2.0, (double)(note - 12) / 12.0);
        playSound(instrument.getSound(), pitch, volume);
    }

    public void playPentatonic(Instrument instrument, float volume) {
        playPentatonic(instrument, volume, 0);
    }

    public void playPentatonic(Instrument instrument, float volume, int offset) {
        playNote(instrument, PENTATONIC[(DarkKore.RANDOM.nextInt(PENTATONIC.length) + offset) % 24], volume);
    }

    public void playSound(SoundEvent sound, float pitch, float volume) {
        MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(sound, pitch, volume));
    }

}
