package io.github.darkkronicle.darkkore.colors;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.darkkronicle.darkkore.config.impl.ConfigObject;
import io.github.darkkronicle.darkkore.util.Color;
import io.github.darkkronicle.darkkore.util.ColorUtil;
import io.github.darkkronicle.darkkore.util.render.ShaderHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.With;
import net.minecraft.client.render.Shader;
import net.minecraft.util.Util;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class ExtendedColor extends ColorAlias {

    @Getter
    private ChromaOptions chroma;


    private static float LAST_GAME_TIME = 0;

    @Data
    @AllArgsConstructor
    public static class ChromaOptions {

        public static String DISPLAY_KEY = "darkkore.optiontype.chroma";
        public static String INFO_KEY = "darkkore.optiontype.info.chroma";

        @With
        private final boolean active;
        @With
        private final float opacity;
        @With
        private final float size;
        @With
        private final float speed;
        @With
        private final float saturation;

        public static ChromaOptions getDefault() {
            return new ChromaOptions(false, 1, 0.5f, 0.5f, 1);
        }

        public ChromaOptions copy() {
            return new ChromaOptions(active, opacity, size, speed, saturation);
        }

        public void save(ConfigObject object) {
            object.set("active", active);
            object.set("opacity", opacity);
            object.set("size", size);
            object.set("speed", speed);
            object.set("saturation", saturation);
        }

        public static ChromaOptions load(ConfigObject object, ChromaOptions defaultValue) {
            AtomicBoolean active = new AtomicBoolean(defaultValue.isActive());
            AtomicReference<Float> opacity = new AtomicReference<>(defaultValue.getOpacity());
            AtomicReference<Float> size = new AtomicReference<>(defaultValue.getSize());
            AtomicReference<Float> speed = new AtomicReference<>(defaultValue.getSpeed());
            AtomicReference<Float> saturation = new AtomicReference<>(defaultValue.getSaturation());
            // ~~Could techincally caste to Double then use .floatValue, but I think this works fine~~
            // IT DID NOT WORK FINE
            object.getOptional("active").ifPresent((opt) -> active.set((boolean) opt));
            object.getOptional("opacity").ifPresent((opt) -> opacity.set(((Number) opt).floatValue()));
            object.getOptional("size").ifPresent((opt) -> size.set(((Number) opt).floatValue()));
            object.getOptional("speed").ifPresent((opt) -> speed.set(((Number) opt).floatValue()));
            object.getOptional("saturation").ifPresent((opt) -> saturation.set(((Number) opt).floatValue()));
            return new ChromaOptions(active.get(), opacity.get(), size.get(), speed.get(), saturation.get());
        }

        /**
         * Sets the color modulation in {@link RenderSystem}. This tells the shaders what values to use for size/speed/saturation/opacity
         */
        public void setColorModulation() {
            RenderSystem.setShaderColor(size, speed, saturation, opacity);
        }

    }

    public ExtendedColor withChroma(ChromaOptions options) {
        if (this.isAlias()) {
            return new ExtendedColor(getAliasName(), options);
        }
        return new ExtendedColor(rawColor(), options);
    }

    public ExtendedColor(String alias, ChromaOptions chroma) {
        super(alias);
        this.chroma = chroma;
    }

    public ExtendedColor(Color color, ChromaOptions chroma) {
        super(color);
        this.chroma = chroma;
    }

    public ExtendedColor(int color, ChromaOptions chroma) {
        super(color);
        this.chroma = chroma;
    }

    public ExtendedColor(int red, int green, int blue, int alpha, ChromaOptions chroma) {
        super(red, green, blue, alpha);
        this.chroma = chroma;
    }

    public ExtendedColor(int red, int green, int blue, int alpha, int color, ChromaOptions chroma) {
        super(red, green, blue, alpha, color);
        this.chroma = chroma;
    }

    @Override
    public int red() {
        if (!chroma.isActive()) {
            return super.red();
        }
        return color() >> 16 & 0xFF;
    }

    @Override
    public int green() {
        if (!chroma.isActive()) {
            return super.green();
        }
        return color() >> 8 & 0xFF;
    }

    @Override
    public int blue() {
        if (!chroma.isActive()) {
            return super.blue();
        }
        return color() & 0xFF;
    }

    @Override
    public int alpha() {
        if (!chroma.isActive()) {
            return super.alpha();
        }
        return (int) (chroma.getOpacity() * 255);
    }

    @Override
    public int color() {
        if (!chroma.isActive()) {
            return super.color();
        }
        float size = 30 * (1 - chroma.getSize());
        float speed = 1000 * chroma.getSpeed();
        float saturation = chroma.getSaturation();

        float timeInTicks = ((float) Util.getMeasuringTimeMs()) / 50;
        float time = (timeInTicks % 24000L) / 24000.0f;

        double mult = size * time * speed;

        float r = (float) (Math.cos(mult + 0) * .6 + .6);
        float g = (float) ((mult + 23) * .6 + .6);
        float b = (float) ((mult + 21) * .6 + .6);
        float a = chroma.getOpacity();

        if (saturation < 1) {
            float gray = r * .299f + g * .587f + b * .114f;
            if (saturation <= 0) {
                r = gray;
                g = gray;
                b = gray;
            } else {
                r = (gray) * (saturation - 1) + (r * saturation);
                g = (gray) * (saturation - 1) + (g * saturation);
                b = (gray) * (saturation - 1) + (b * saturation);
            }
        }
        return ColorUtil.colorToInt4f(r, g, b, a);

    }

    @Override
    public Supplier<Shader> getShader() {
        if (!chroma.isActive()) {
            return super.getShader();
        }
        return ShaderHandler.CustomShader.CHROMA::getShader;
    }

    @Override
    public int preRender() {
        if (!chroma.isActive()) {
            return super.preRender();
        }
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(getShader());
        LAST_GAME_TIME = RenderSystem.getShaderGameTime();
        float timeInTicks = ((float) Util.getMeasuringTimeMs()) / 50;
        RenderSystem.setShaderGameTime((int) timeInTicks % 24000, timeInTicks % 1);
        chroma.setColorModulation();
        return -1;
    }

    @Override
    public void postRender() {
        if (!chroma.isActive()) {
            super.postRender();
            return;
        }
        float time = LAST_GAME_TIME * 24000;
        float tickDelta = time % 1;
        int ticks = (int) time;
        RenderSystem.setShaderGameTime(ticks, tickDelta);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }
}
