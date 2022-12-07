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

/**
 * Utility class to interface with Minecraft's sounds
 */
@UtilityClass
public class SoundUtil {

    private final String[] NOTES = new String[]{"F#", "G", "G#", "A", "A#", "B", "C", "C#", "D", "D#", "E", "F"};
    private final int[] PENTATONIC = new int[]{0, 2, 4, 7, 9, 12, 14, 16, 19, 21, 24};

    /**
     * Plays the default interface sound (like a button click)
     */
    public void playInterfaceSound() {
        playInterfaceSound(DarkKoreConfig.getInstance().soundType.getValue().getInstrument());
    }

    /**
     * Plays an interface sound with a specific {@link Instrument} (can be null). If null it will play the generic button click sound
     * @param instrument {@link Instrument} to play the sound
     */
    public void playInterfaceSound(@Nullable Instrument instrument) {
        if (instrument == null) {
            playSound(SoundEvents.UI_BUTTON_CLICK.value(), 1, 3f);
        } else {
            playPentatonic(instrument, 3f);
        }
    }

    /**
     * Plays a specific note from an {@link Instrument}. Note 0 is F#, goes to 24
     * @param instrument {@link Instrument} to play the note
     * @param note The note to play
     * @param volume Volume that should be played at
     */
    public void playNote(Instrument instrument, int note, float volume) {
        float pitch = (float)Math.pow(2.0, (double)(note - 12) / 12.0);
        playSound(instrument.getSound().value(), pitch, volume);
    }

    /**
     * Plays a random note from the pentatonic scale
     * @param instrument {@link Instrument} to play it
     * @param volume Volume that should be played at
     */
    public void playPentatonic(Instrument instrument, float volume) {
        playPentatonic(instrument, volume, 0);
    }

    /**
     * Plays a random note from the pentatonic scale with a specified shift
     * @param instrument {@link Instrument} to play it
     * @param offset The amount of notes (chromatic) it should be shifted by
     * @param volume Volume that should be played at
     */
    public void playPentatonic(Instrument instrument, float volume, int offset) {
        playNote(instrument, PENTATONIC[(DarkKore.RANDOM.nextInt(PENTATONIC.length) + offset) % 24], volume);
    }

    /**
     * Plays a sound
     * @param sound {@link SoundEvent} to play
     * @param pitch Pitch to play
     * @param volume Volume to play
     */
    public void playSound(SoundEvent sound, float pitch, float volume) {
        MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(sound, pitch, volume));
    }

}
